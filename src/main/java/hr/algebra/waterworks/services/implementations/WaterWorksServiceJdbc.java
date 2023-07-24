package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.config.ImageConfig;
import hr.algebra.waterworks.dao.entities.Category;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.dao.entities.Login;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.LoginDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
    private static final String SELECT_ALL_LOGINS = "SELECT l.*, U.FIRST_NAME AS USER_FIRST_NAME, U.LAST_NAME AS USER_LAST_NAME, u.EMAIL AS USER_EMAIL, r.NAME AS ROLE_NAME FROM USER_ACCOUNT_LOGIN l INNER JOIN USER_ACCOUNT AS U ON U.ID = l.USER_ID INNER JOIN ROLE AS R ON R.ID = U.ROLE_ID";

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert itemJdbcInsert;
    private SimpleJdbcInsert loginJdbcInsert;

    @Autowired
    public WaterWorksServiceJdbc(JdbcTemplate jdbcTemplate, ImageConfig imageConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ITEM_TABLE_NAME)
                .usingGeneratedKeyColumns(ITEM_TABLE_ID);
        this.loginJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_ACCOUNT_LOGIN")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<ItemDto> getAllItems(ItemFilterRequest request) {

        String query = SELECT_ALL_ITEMS_QUERY;
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
    public void createLogin(Login login) {
        Map<String, Object> loginDetails = new HashMap<>();
        loginDetails.put("USER_ID", login.getUserId());
        loginDetails.put("IP_ADDRESS", login.getIpAddress());
        loginDetails.put("LOCAL_DATETIME", login.getDateTime());
        loginJdbcInsert.execute(loginDetails);
    }

    private LoginDto mapRowToLogin(ResultSet rs, int rowNum) throws SQLException{
        return new LoginDto(rs.getInt("USER_ID"),
                rs.getString("IP_ADDRESS"),
                (rs.getTimestamp("LOCAL_DATETIME")).toLocalDateTime(),
                rs.getString("USER_EMAIL"),
                rs.getString("USER_FIRST_NAME"),
                rs.getString("USER_LAST_NAME"),
                rs.getString("ROLE_NAME"));
    }

    @Override
    public List<LoginDto> getAllLogins() {
        return jdbcTemplate.query(SELECT_ALL_LOGINS, this::mapRowToLogin);
    }
}
