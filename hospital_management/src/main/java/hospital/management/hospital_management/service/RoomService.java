package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.RoomEntity;
import hospital.management.hospital_management.dto.request.RoomRequest;
import hospital.management.hospital_management.dto.response.RoomResponse;
import hospital.management.hospital_management.helper.RoomServiceHelper;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.RoomRepository;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final DepartmentRepository departmentRepository;
    private final RoomServiceHelper roomServiceHelper;

    @Transactional
    public RoomResponse saveRoom(RoomRequest roomRequest) throws CustomException {
        this.roomServiceHelper.checkValidInfoRoom(roomRequest);
        RoomEntity roomEntity=new RoomEntity();
        if(roomRequest.getId()!=null){
            roomEntity=this.roomRepository.findById(roomRequest.getId()).get();
        }
        DepartmentEntity currentDepartment=this.departmentRepository.findById(roomRequest.getDepartmentId()).get();
        roomEntity.setRoomNumber(roomRequest.getRoomNumber());
        roomEntity.setRoomType(roomRequest.getRoomType());
        roomEntity.setNumberOfBeds(roomRequest.getNumberOfBeds());
        roomEntity.setDepartmentRoom(currentDepartment);
        roomEntity.setIsActive(true);
        this.roomRepository.save(roomEntity);
        RoomResponse roomResponse=this.roomServiceHelper.convertToRoomResponse(roomEntity);
        return roomResponse;
    }

    @Transactional
    public void changeActiveRoom(Long roomId) throws CustomException{
        RoomEntity currentRoom=this.roomRepository.findById(roomId).get();
        if(currentRoom==null){
            throw new CustomException("Phòng khám không tồn tại");
        }
        if(currentRoom.getIsActive()){
            currentRoom.setIsActive(false);
        }else{
            currentRoom.setIsActive(true);
        }
        this.roomRepository.save(currentRoom);
    }


}
