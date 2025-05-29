package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.RoomEntity;
import hospital.management.hospital_management.dto.request.RoomRequest;
import hospital.management.hospital_management.dto.response.RoomResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.RoomRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomServiceHelper {
    private final RoomRepository roomRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public void checkValidInfoRoom(RoomRequest roomRequest) throws CustomException {
        if(roomRequest.getNumberOfBeds()<=0){
            throw new CustomException("Số phòng không được bé hơn hoặc bằng 0");
        }
        RoomEntity currentRoom=this.roomRepository.findByRoomNumberAndDepartmentRoom_Id(roomRequest.getRoomNumber(),roomRequest.getDepartmentId());
        DepartmentEntity currentDepartment=this.departmentRepository.findById(roomRequest.getDepartmentId()).get();
        if(currentDepartment==null){
            throw new CustomException("Khoa không tồn tại");
        }
        if(currentRoom!=null && roomRequest.getId()==null){
            throw new CustomException("Khoa "+currentDepartment.getDepartmentName()+" đã tồn tại phòng "+currentRoom.getRoomNumber());
        }
        if(roomRequest.getRoomType()==null){
            throw new CustomException("Loại phòng không được để trống");
        }
    }

    public RoomResponse convertToRoomResponse(RoomEntity roomEntity){
        RoomResponse roomResponse=new RoomResponse();
        modelMapper.map(roomEntity,roomResponse);
        return roomResponse;
    }
}
