package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Long>, JpaSpecificationExecutor<RoomEntity> {
    RoomEntity findByRoomNumber(String roomNumber);
    RoomEntity findByRoomNumberAndDepartmentRoom_Id(String roomNumber, Long id);

}
