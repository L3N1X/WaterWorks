package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.LoginRequest;
import hr.algebra.waterworks.shared.requests.RegisterRequest;

public interface RegistrationService {
    void registerNewUser(RegisterRequest request, String authority);
}
