package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ApiMessage("Create a user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userInfo){
        return ResponseEntity.ok().body(this.userService.createUser(userInfo));
    }
}
