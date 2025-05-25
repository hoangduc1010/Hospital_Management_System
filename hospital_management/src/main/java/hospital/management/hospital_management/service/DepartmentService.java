package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.dto.response.DepartmentResponse;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.helper.DepartmentServiceHelper;
import hospital.management.hospital_management.helper.PaginationHelper;
import hospital.management.hospital_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentServiceHelper departmentServiceHelper;
    private final PaginationHelper paginationHelper;
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
}
