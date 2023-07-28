package hr.algebra.waterworks.services.implementations;

import hr.algebra.waterworks.services.interfaces.RegistrationService;
import hr.algebra.waterworks.shared.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationServiceJdbc implements RegistrationService {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userJdbcInsert;
    private SimpleJdbcInsert authorityJdbcInsert;

    @Autowired
    public RegistrationServiceJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users");
        this.authorityJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("authorities");
    }

    @Override
    public void registerNewUser(RegisterRequest request, String authority) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", request.getUsername());
        userDetails.put("FIRST_NAME", request.getFirstName());
        userDetails.put("LAST_NAME", request.getLastName());
        userDetails.put("password", new BCryptPasswordEncoder().encode(request.getPassword()));
        userDetails.put("ENABLED", true);
        userJdbcInsert.execute(userDetails);
        Map<String, Object> authorityDetails = new HashMap<>();
        authorityDetails.put("username", request.getUsername());
        authorityDetails.put("authority", authority);
        authorityJdbcInsert.execute(authorityDetails);
    }
}
