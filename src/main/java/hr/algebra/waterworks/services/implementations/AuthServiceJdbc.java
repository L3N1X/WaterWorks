package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.config.ImageConfig;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.services.interfaces.AuthService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.LoginDto;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceJdbc implements AuthService {

    private final JdbcTemplate jdbcTemplate;
    private final String SELECT_USERS_BY_AUTH_DATA = "SELECT u.*, r.NAME AS ROLE_NAME FROM USER_ACCOUNT AS u INNER JOIN ROLE AS r ON r.ID = u.ROLE_ID WHERE 1=1";

    private String hashPassword(String password){
        return password;
    }

    @Autowired
    public AuthServiceJdbc(JdbcTemplate jdbcTemplate, ImageConfig imageConfig) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto tryAuthenticate(LoginRequest request) {

        Optional<UserDto> user= jdbcTemplate.query(SELECT_USERS_BY_AUTH_DATA +
                " AND u.EMAIL = '" +
                request.getEmail() +
                "' AND u.PASSWORD_HASH = '" +
                hashPassword(request.getPassword()) + "'",this::mapRowToUser).stream().findFirst();
        return user.orElse(null);
    }

    private UserDto mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new UserDto(rs.getInt("ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("ROLE_NAME"));
    }
}
