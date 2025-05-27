package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.NurseEntity;
import hospital.management.hospital_management.dto.request.DepartmentRequest;
import hospital.management.hospital_management.dto.response.DepartmentResponse;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.helper.DepartmentServiceHelper;
import hospital.management.hospital_management.helper.PaginationHelper;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.NurseRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentServiceHelper departmentServiceHelper;
    private final PaginationHelper paginationHelper;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    public <T> ResponsePaginationDTO<T> getAllDepartment(Specification specification, Pageable pageable) {
        Page<T> departmentPage = this.departmentRepository.findAll(specification, pageable);
        List<DepartmentEntity> departments= (List<DepartmentEntity>) departmentPage.getContent();
        List<DepartmentResponse> departmentResponses=new ArrayList<>();
        for(DepartmentEntity department:departments){
            DepartmentResponse departmentResponse=this.departmentServiceHelper.convertToDepartmentResponse(department);
            departmentResponses.add(departmentResponse);
        }
        return this.paginationHelper.getAllPagination(departmentPage, (List<T>) departmentResponses, pageable);
    }

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) throws CustomException {
        if(departmentRequest.getDepartmentName()==null){
            throw new CustomException("Tên khoa không được để trống");
        }
        DepartmentEntity currentDepartment=this.departmentRepository.findByDepartmentNameIgnoreCase(departmentRequest.getDepartmentName());
        DepartmentResponse departmentResponse=new DepartmentResponse();
        if(currentDepartment!=null){
            throw new CustomException("Tên khoa đã bị trùng");
        }
        currentDepartment=new DepartmentEntity();
        modelMapper.map(departmentRequest,currentDepartment);
        this.departmentRepository.save(currentDepartment);
        modelMapper.map(currentDepartment,departmentResponse);
        return departmentResponse;
    }

    public DepartmentResponse updateDepartment(DepartmentRequest departmentRequest) throws CustomException{
        if(departmentRequest.getDepartmentId()==null){
            throw new CustomException("Id của khoa không được bỏ trống");
        }
        DepartmentEntity currentDepartment=this.departmentRepository.findById(departmentRequest.getDepartmentId()).get();
        DepartmentEntity anotherDepartment=this.departmentRepository.findByDepartmentNameIgnoreCase(departmentRequest.getDepartmentName());
        if(anotherDepartment!=null && anotherDepartment.getId() != departmentRequest.getDepartmentId()){
            throw new CustomException("Tên khoa đã bị trùng");
        }
        DepartmentResponse departmentResponse=new DepartmentResponse();
        currentDepartment.setDepartmentName(departmentRequest.getDepartmentName());
        currentDepartment.setDescription(departmentRequest.getDescription());
        this.departmentRepository.save(currentDepartment);
        modelMapper.map(currentDepartment,departmentResponse);
        return departmentResponse;
    }

    public void changeActiveDepartment(Long departmentId) throws CustomException{
        DepartmentEntity currentDepartment=this.departmentRepository.findById(departmentId).get();
        if(currentDepartment==null){
            throw new CustomException("Khoa không tồn tại");
        }
        if(currentDepartment.getIsActive()==true && currentDepartment.getPatients().size()>0){
            throw new CustomException("Khoa "+currentDepartment.getDepartmentName() +" đang có bệnh nhân, không thể đóng");
        }
        if(currentDepartment.getIsActive()==false){
            currentDepartment.setIsActive(true);
        }else{
            Set<DoctorEntity> doctors = currentDepartment.getDoctors();
            for (DoctorEntity doctor : doctors) {
                doctor.setDepartments(null);
            }
            Set<NurseEntity> nurses = currentDepartment.getNurses();
            for (NurseEntity nurse : nurses) {
                nurse.setDepartments(null);
            }
            this.doctorRepository.saveAll(doctors);
            this.nurseRepository.saveAll(nurses);
            currentDepartment.setIsActive(false);
        }
        this.departmentRepository.save(currentDepartment);
    }
}
