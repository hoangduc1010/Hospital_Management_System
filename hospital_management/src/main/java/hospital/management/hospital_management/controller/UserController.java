package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RoleAccess(allowedRoles = {RoleEnum.ADMIN})
public class UserController {
    private final UserService userService;

    @PostMapping
    @ApiMessage("Tạo mới ngừoi dùng")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userInfo) throws CustomException {
        return ResponseEntity.ok().body(this.userService.createUser(userInfo));
    }

    @PutMapping
    @ApiMessage("Cập nhật người dùng")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userInfo) throws CustomException{
        return ResponseEntity.ok().body(this.userService.updateUser(userInfo));
    }



}
