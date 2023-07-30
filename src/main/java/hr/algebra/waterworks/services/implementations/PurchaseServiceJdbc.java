package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.services.interfaces.PurchaseService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseServiceJdbc implements PurchaseService {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert receiptJdbcInsert;
    private SimpleJdbcInsert receiptRecordJdbcInsert;

    @Autowired
    public PurchaseServiceJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.receiptJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECEIPT");
        this.receiptRecordJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECEIPT_RECORD");
    }

    @Override
    public void initPurchaseFromCart(Cart cart) {
        for (ItemDto key : cart.getItems().keySet()) {
            Integer value = cart.getItems().get(key);
            // use key and value
        }
        Map<String, Object> receiptRecordDetails = new HashMap<>();
    }
}
