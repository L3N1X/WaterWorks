package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.LoginRequest;

import java.util.Optional;

public interface AuthService {
    UserDto tryAuthenticate(LoginRequest request);
}
