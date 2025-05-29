package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.RoomRequest;
import hospital.management.hospital_management.dto.response.RoomResponse;
import hospital.management.hospital_management.service.RoomService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@RoleAccess(allowedRoles = {RoleEnum.ADMIN})
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ApiMessage("Tạo và cập nhật phòng khám")
    public ResponseEntity<RoomResponse> saveRoom(@RequestBody RoomRequest roomInfo) throws CustomException {
        return ResponseEntity.ok().body(this.roomService.saveRoom(roomInfo));
    }

    @DeleteMapping("/{roomId}")
    @ApiMessage("Thay đổi trạng thái phòng khám")
    public ResponseEntity<Void> changeActiveRoom(@PathVariable Long roomId) throws CustomException{
        this.roomService.changeActiveRoom(roomId);
        return ResponseEntity.ok().build();
    }



}
