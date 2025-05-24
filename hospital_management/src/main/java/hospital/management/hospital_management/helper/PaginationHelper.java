package hospital.management.hospital_management.helper;

import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaginationHelper {
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    public <T> ResponsePaginationDTO<T> getAllPagination(Page<T> page,List<T> listEntity, Pageable pageable) {
        ResponsePaginationDTO<T> resultPaginationDTO = new ResponsePaginationDTO<>();
        ResponsePaginationDTO.Meta meta = new ResponsePaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(listEntity);
        return resultPaginationDTO;
    }

}
