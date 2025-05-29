package hospital.management.hospital_management.config;


import hospital.management.hospital_management.domain.PermissionEntity;
import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.repository.PermissionRepository;
import hospital.management.hospital_management.repository.RoleRepository;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.HttpMethodEnum;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private  PermissionRepository permissionRepository;
    @Autowired
    private  HttpServletRequest request;
    @Autowired
    private  UserService userService;
    @Autowired
    private  SecurityUtil securityUtil;
    @Autowired
    private  RoleRepository roleRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws CustomException {
        if (handler instanceof HandlerMethod method) {
            RoleAccess roleAccess = method.getMethodAnnotation(RoleAccess.class) == null ?
                    method.getBeanType().getAnnotation(RoleAccess.class) : method.getMethodAnnotation(RoleAccess.class);

            if (roleAccess != null) {
                String path = request.getRequestURI();
                String methodType = request.getMethod();
                HttpMethodEnum methodEnum=HttpMethodEnum.valueOf(methodType.toUpperCase());
                Optional<String> username = SecurityUtil.getCurrentUserLogin();
                if(username.equals("")){
                    throw new CustomException("Tài khoản không hợp lệ!");
                }
                UserEntity currentUser=this.userService.findByUsername(username.get());
                String userRoleName = String.valueOf(currentUser.getRole().getRoleName());
                List<RoleEnum> allowedRoles = Arrays.asList(roleAccess.allowedRoles());
                if (!allowedRoles.contains(RoleEnum.valueOf(userRoleName))) {
                    throw new CustomException("Bạn không có quyền truy cập!");
                }
                PermissionEntity permission = permissionRepository.findByApiPathAndMethod(path, methodEnum);
                if (permission == null) {
                    permission = new PermissionEntity();
                    permission.setApiPath(path);
                    permission.setMethod(methodEnum);
                    permission=permissionRepository.save(permission);
                }
                boolean isAllowed = false;
                for(RoleEnum role:allowedRoles){
                    RoleEnum roleEnum = RoleEnum.valueOf(role.toString());
                    RoleEntity perRole = this.roleRepository.findByRoleName(roleEnum);
                    Boolean isExists=false;
                    for(PermissionEntity permissionEntity:perRole.getPermissions()){
                        if(permissionEntity.getMethod().equals(permission.getMethod()) &&
                        permissionEntity.getApiPath().equals(permission.getApiPath())){
                            isExists=true;
                        }
                    }
                    if(isExists==false){
                        perRole.getPermissions().add(permission);
                        roleRepository.save(perRole);
                        System.out.println("Thêm api path : "+permission.getApiPath()+" và method: "+permission.getMethod());
                    }
                    List<PermissionEntity> permissionRole=perRole.getPermissions();
                    boolean hasPermission = permissionRole.stream()
                            .anyMatch(p -> p.getApiPath().equals(path) && p.getMethod().equals(methodEnum));
                    if (hasPermission) {
                        isAllowed = true;
                        break;
                    }
                }
                if(!isAllowed){
                    throw new CustomException("Bạn không có quyền truy cập!");
                }else{
                    return true;
                }
            }else{
                throw new CustomException("Bạn không có quyền truy cập!");
            }
        }else{
            throw new CustomException("Bạn không có quyền truy cập!");
        }
    }
}
