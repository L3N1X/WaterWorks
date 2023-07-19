package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class WaterWorksServiceJdbc implements WaterWorksService {

    private static final String ITEM_TABLE_NAME = "ITEM";
    private static final String ITEM_TABLE_ID = "Id";
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
        return null;
    }

    @Override
    public ItemDto getItem(int id) {
        return null;
    }

    @Override
    public ItemDto createItem(CreateItemRequest request) {
        return null;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return null;
    }
}
