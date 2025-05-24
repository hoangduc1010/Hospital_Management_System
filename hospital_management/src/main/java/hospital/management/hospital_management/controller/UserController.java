package hospital.management.hospital_management.controller;


import com.turkraft.springfilter.boot.Filter;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userInfo) throws CustomException {
        return ResponseEntity.ok().body(this.userService.updateUser(userInfo));
    }

    @DeleteMapping("/{userId}")
    @ApiMessage("Thay đổi trạng thái hoạt động tài khoản")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws CustomException {
        this.userService.changeActiveUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{userId}")
    @ApiMessage("Lấy người dùng theo id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) throws CustomException {
        return ResponseEntity.ok().body(this.userService.getUserById(userId));
    }

    @GetMapping("/find/{username}")
    @ApiMessage("Lấy người dùng theo username")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) throws CustomException {
        return ResponseEntity.ok().body(this.userService.getUserByUsername(username));
    }

    @GetMapping("/find/all")
    @ApiMessage("Lấy tất cả người dùng")
    public ResponseEntity<ResponsePaginationDTO> getAllUser(@Filter Specification<UserEntity> specification,
                                                            Pageable pageable) {
        return ResponseEntity.ok().body(this.userService.getAll(specification,pageable));
    }


}
