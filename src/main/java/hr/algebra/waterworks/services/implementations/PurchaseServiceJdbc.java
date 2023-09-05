package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.dao.enums.PurchaseType;
import hr.algebra.waterworks.services.interfaces.PurchaseService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.PurchaseRecordDto;
import hr.algebra.waterworks.shared.dtos.ReceiptDto;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.ReceiptFilterRequest;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PurchaseServiceJdbc implements PurchaseService {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert receiptJdbcInsert;
    private SimpleJdbcInsert receiptRecordJdbcInsert;

    @Autowired
    public PurchaseServiceJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.receiptJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECEIPT")
                .usingGeneratedKeyColumns("ID");
        this.receiptRecordJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECEIPT_RECORD")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public String initPurchaseFromCart(Cart cart, PurchaseType purchaseType, UserDto user) {
        String receiptNumber = "IN" + UUID.randomUUID();
        Map<String, Object> receiptDetails = new HashMap<>();
        receiptDetails.put("RECEIPT_NUMBER", receiptNumber);
        receiptDetails.put("USER_ID", user.username());
        receiptDetails.put("USER_FULL_NAME", user.firstName() + ' ' + user.lastName());
        receiptDetails.put("TOTAL_PRICE", cart.getTotalPrice());
        receiptDetails.put("PURCHASE_TYPE", purchaseType.getIntValue());
        receiptDetails.put("LOCAL_DATETIME", LocalDateTime.now());
        int receiptId = receiptJdbcInsert.execute(receiptDetails);
        for (ItemDto item : cart.getItems().keySet()) {

            Integer value = cart.getItems().get(item);
            int itemAmountAfterPurchase = item.amount() - value;
            final String amountDecrementQuery = "UPDATE ITEM SET AMOUNT = ? WHERE ID = ?";
            jdbcTemplate.update(amountDecrementQuery, itemAmountAfterPurchase, item.id());

            Map<String, Object> receiptRecordDetails = new HashMap<>();
            receiptRecordDetails.put("PRICE", item.price());
            receiptRecordDetails.put("AMOUNT", value);
            receiptRecordDetails.put("ITEM_ID", item.id());
            receiptRecordDetails.put("RECEIPT_ID", receiptId);
            receiptRecordJdbcInsert.execute(receiptRecordDetails);
        }
        return receiptNumber;
    }

    private final String SELECT_RECEIPT_BY_USERNAME_QUERY = "SELECT * FROM RECEIPT WHERE USER_ID = ";
    @Override
    public List<ReceiptDto> getReceipts(String username) {
        List<ReceiptDto> receipts = jdbcTemplate.query(SELECT_RECEIPT_BY_USERNAME_QUERY +  "'" + username + "'", this::mapRowToReceipt);
        for (ReceiptDto receipt : receipts) {
            receipt.setPurchaseRecords(getPurchaseRecords(receipt.getId()));
        }
        return receipts;
    }

    private ReceiptDto mapRowToReceipt(ResultSet rs, int rowNum) throws SQLException {
        return new ReceiptDto(rs.getInt("ID"),
                rs.getString("RECEIPT_NUMBER"),
                rs.getString("USER_ID"),
                rs.getString("USER_FULL_NAME"),
                null,
                rs.getBigDecimal("TOTAL_PRICE"),
                rs.getInt("PURCHASE_TYPE") == 1 ? PurchaseType.CASH : PurchaseType.PAYPAL,
                (rs.getTimestamp("LOCAL_DATETIME")).toLocalDateTime());
    }

    private final String SELECT_RECEIPTS_QUERY = "SELECT * FROM RECEIPT WHERE 1=1";
    @Override
    public List<ReceiptDto> getAllReceipts() {
        List<ReceiptDto> receipts = jdbcTemplate.query(SELECT_RECEIPTS_QUERY, this::mapRowToReceipt);
        for (ReceiptDto receipt : receipts) {
            receipt.setPurchaseRecords(getPurchaseRecords(receipt.getId()));
        }
        return receipts;
    }

    @Override
    public List<ReceiptDto> getAllReceiptsFiltered(ReceiptFilterRequest filterRequest) {
        String query = SELECT_RECEIPTS_QUERY;
        if(filterRequest.getDateFrom() != null)
            query += " AND LOCAL_DATETIME >= '" + filterRequest.getDateFrom().atStartOfDay() + "'";
        if(filterRequest.getDateTo() != null)
            query += " AND LOCAL_DATETIME <= '" + filterRequest.getDateTo().atStartOfDay() + "'";
        List<ReceiptDto> receipts = jdbcTemplate.query(query, this::mapRowToReceipt);
        for (ReceiptDto receipt : receipts) {
            receipt.setPurchaseRecords(getPurchaseRecords(receipt.getId()));
        }
        return receipts;
    }

    private final String SELECT_RECEIPT_RECORDS_BY_RECEIPT_QUERY = "SELECT * FROM RECEIPT_RECORD rr INNER JOIN ITEM AS i on i.ID = rr.ITEM_ID WHERE RECEIPT_ID = ";

    private List<PurchaseRecordDto> getPurchaseRecords(int receiptId) {
        return jdbcTemplate.query(SELECT_RECEIPT_RECORDS_BY_RECEIPT_QUERY + receiptId, this::mapRowToPurchaseRecord);
    }

    private PurchaseRecordDto mapRowToPurchaseRecord(ResultSet rs, int rowNum) throws SQLException {
        return new PurchaseRecordDto(
                rs.getString("NAME"),
                rs.getInt("AMOUNT"),
                rs.getBigDecimal("PRICE"));
    }
}
