package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.dao.entities.Category;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public WaterWorksServiceJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ITEM_TABLE_NAME)
                .usingGeneratedKeyColumns(ITEM_TABLE_ID);
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
    public ItemDto createItem(CreateItemRequest request) {
        int id = insertItem(request);
        List<Item> items = jdbcTemplate.query(SELECT_ITEM_BY_ID + id, this::mapRowToItem);
        Optional<ItemDto> dto = itemsToDtos(items).stream().findFirst();
        return dto.orElse(null);
    }

    private int insertItem(CreateItemRequest request) {
        Map<String, Object> itemDetails = new HashMap<>();
        itemDetails.put("NAME", request.getName());
        itemDetails.put("DESCRIPTION", request.getDescription());
        itemDetails.put("PRICE", request.getPrice());
        itemDetails.put("CATEGORY_ID", request.getCategoryId());
        itemDetails.put("AMOUNT", request.getAmount());
        return simpleJdbcInsert.executeAndReturnKey(itemDetails).intValue();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = jdbcTemplate.query(SELECT_ALL_CATEGORIES, this::mapRowToCategory);
        return categoriesToDtos(categories);
    }
}
