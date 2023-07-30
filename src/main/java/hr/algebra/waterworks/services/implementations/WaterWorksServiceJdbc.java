package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.config.ImageConfig;
import hr.algebra.waterworks.dao.entities.Category;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.dao.entities.Login;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.LoginDto;
import hr.algebra.waterworks.shared.requests.CreateCategoryRequest;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.EditItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Service
public class WaterWorksServiceJdbc implements WaterWorksService {

    private static final String ITEM_TABLE_NAME = "ITEM";
    private static final String ITEM_TABLE_ID = "Id";
    private static final String SELECT_ALL_ITEMS_QUERY = "SELECT * FROM ITEM WHERE 1=1 ";
    private static final String SELECT_ITEM_BY_ID = "SELECT * FROM ITEM WHERE ID =";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM CATEGORY WHERE ID =";
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM CATEGORY";
    private static final String SELECT_ALL_LOGINS = "SELECT ual.*,  u.FIRST_NAME AS USER_FIRST_NAME, u.LAST_NAME AS USER_LAST_NAME FROM USER_ACCOUNT_LOGIN AS ual INNER JOIN users AS u ON u.username = ual.USER_ID";

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert itemJdbcInsert;
    private SimpleJdbcInsert loginJdbcInsert;
    private SimpleJdbcInsert categoryJdbcInsert;

    @Autowired
    public WaterWorksServiceJdbc(JdbcTemplate jdbcTemplate, ImageConfig imageConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ITEM_TABLE_NAME)
                .usingGeneratedKeyColumns(ITEM_TABLE_ID);
        this.loginJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_ACCOUNT_LOGIN")
                .usingGeneratedKeyColumns("ID");
        this.categoryJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CATEGORY")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<ItemDto> getAllItems(ItemFilterRequest request, Optional<Boolean> active) {

        String query = SELECT_ALL_ITEMS_QUERY;
        if(active.isPresent()){
            if(active.get() == true)
                query += " AND ACTIVE = true";
            else
                query += " AND ACTIVE = false";
        }
        if(request != null){
            if(Optional.ofNullable(request.getName()).isPresent() && !request.getName().isBlank()){
                query += " AND NAME = '" + request.getName() + "'";
            }
            if(request.getSelectedCategoryId() != 0){
                query += " AND CATEGORY_ID = "  + request.getSelectedCategoryId();
            }
            if(Optional.ofNullable(request.getPriceFrom()).isPresent()){
                query += " AND PRICE >= " + request.getPriceFrom();
            }
            if(Optional.ofNullable(request.getPriceTo()).isPresent()) {
                query += " AND PRICE <= " + request.getPriceTo();
            }
            if (request.getSelectedStatusId() == 1)
                query += " AND AMOUNT > 0";
            else if (request.getSelectedStatusId() == 2) {
                query += " AND AMOUNT = 0";
            }
        }
        List<Item> items = jdbcTemplate.query(query, this::mapRowToItem);
        return itemsToDtos(items);
    }

    private List<ItemDto> itemsToDtos(List<Item> items){
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items)
        {
            String currentCategoryName = "";
            Optional<Category> category = jdbcTemplate.query(SELECT_CATEGORY_BY_ID + item.getCategoryId(), this::mapRowToCategory).stream().findFirst();
            if(category.isPresent())
                currentCategoryName = category.get().getName();
            ItemDto dto = new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), currentCategoryName, item.getImageName(), item.isActive(), true, item.getAmount());
            itemDtos.add(dto);
        }
        return itemDtos;
    }

    private List<CategoryDto> categoriesToDtos(List<Category> categories){
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(new CategoryDto(category.getId(), category.getName()));
        }
        return categoryDtos;
    }

    private Item mapRowToItem(ResultSet rs, int rowNum) throws SQLException  {
        return new Item(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("DESCRIPTION"),
                rs.getBigDecimal("PRICE"),
                rs.getInt("CATEGORY_ID"),
                rs.getString("IMAGE_NAME"),
                rs.getBoolean("ACTIVE"),
                rs.getInt("AMOUNT")
        );
    }

    private Category mapRowToCategory(ResultSet rs, int rowNum) throws SQLException{
        return new Category(rs.getInt("ID"), rs.getString("NAME"));
    }

    @Override
    public ItemDto getItem(int id) {
        List<Item> items = jdbcTemplate.query(SELECT_ITEM_BY_ID + id, this::mapRowToItem);
        Optional<ItemDto> dto = itemsToDtos(items).stream().findFirst();
        return dto.orElse(null);
    }

    @Override
    public ItemDto createItem(CreateItemRequest request) throws IOException {
        int id = insertItem(request);
        List<Item> items = jdbcTemplate.query(SELECT_ITEM_BY_ID + id, this::mapRowToItem);
        Optional<ItemDto> dto = itemsToDtos(items).stream().findFirst();
        return dto.orElse(null);
    }

    private final String UPDATE_ITEM_QUERY_NEW_IMAGE = "UPDATE ITEM SET NAME = ?, DESCRIPTION = ?, PRICE = ?, IMAGE_NAME = ?, AMOUNT = ?, CATEGORY_ID = ? WHERE ID = ?";
    private final String UPDATE_ITEM_QUERY_OLD_IMAGE = "UPDATE ITEM SET NAME = ?, DESCRIPTION = ?, PRICE = ?, AMOUNT = ?, CATEGORY_ID = ? WHERE ID = ?";

    @Override
    public void editItem(EditItemRequest request) throws IOException {

        if (!request.getImageInput().isEmpty()) {
            String base64image = null;
            base64image = Base64.getEncoder().encodeToString(request.getImageInput().getBytes());
            jdbcTemplate.update(UPDATE_ITEM_QUERY_NEW_IMAGE, request.getName(),
                    request.getDescription(),
                    request.getPrice(),
                    base64image,
                    request.getAmount(),
                    request.getSelectedCategoryId(),
                    request.getId());
        } else {
            jdbcTemplate.update(UPDATE_ITEM_QUERY_OLD_IMAGE, request.getName(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getAmount(),
                    request.getSelectedCategoryId(),
                    request.getId());
        }
    }


    private final String TOGGLE_ACTIVE_QUERY = "UPDATE ITEM SET ACTIVE = ? WHERE ID = ?";
    @Override
    public void toggleActiveItem(int id, boolean active) {
        jdbcTemplate.update(TOGGLE_ACTIVE_QUERY, active, id);
    }

    private int insertItem(CreateItemRequest request) throws IOException {
        String base64image = null;
        if(!request.getImageInput().isEmpty()) {
            base64image = Base64.getEncoder().encodeToString(request.getImageInput().getBytes());
        }
        Map<String, Object> itemDetails = new HashMap<>();
        itemDetails.put("NAME", request.getName());
        itemDetails.put("DESCRIPTION", request.getDescription());
        itemDetails.put("PRICE", request.getPrice());
        itemDetails.put("CATEGORY_ID", request.getSelectedCategoryId());
        itemDetails.put("AMOUNT", request.getAmount());
        itemDetails.put("IMAGE_NAME", base64image);
        return itemJdbcInsert.executeAndReturnKey(itemDetails).intValue();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = jdbcTemplate.query(SELECT_ALL_CATEGORIES, this::mapRowToCategory);
        return categoriesToDtos(categories);
    }

    @Override
    public void createCategory(CreateCategoryRequest request) {
        Map<String, Object> categoryDetails = new HashMap<>();
        categoryDetails.put("NAME", request.getName());
        categoryJdbcInsert.execute(categoryDetails);
    }

    @Override
    public void createLogin(Login login) {
        Map<String, Object> loginDetails = new HashMap<>();
        loginDetails.put("USER_ID", login.getUserId());
        loginDetails.put("IP_ADDRESS", login.getIpAddress());
        loginDetails.put("LOCAL_DATETIME", login.getDateTime());
        loginJdbcInsert.execute(loginDetails);
    }

    private LoginDto mapRowToLogin(ResultSet rs, int rowNum) throws SQLException{
        return new LoginDto(rs.getString("USER_ID"),
                rs.getString("IP_ADDRESS"),
                (rs.getTimestamp("LOCAL_DATETIME")).toLocalDateTime(),
                rs.getString("USER_FIRST_NAME"),
                rs.getString("USER_LAST_NAME"));
    }

    @Override
    public List<LoginDto> getAllLogins() {
        return jdbcTemplate.query(SELECT_ALL_LOGINS, this::mapRowToLogin);
    }
}
