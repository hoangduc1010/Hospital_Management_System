package hospital.management.hospital_management.config;

import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.RoleRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.DepartmentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataLoaderConfig implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count()==0){
            jdbcTemplate.update("INSERT INTO roles (id, role_name)\n" +
                    "VALUES \n" +
                    "  (1, 'PATIENT'),\n" +
                    "  (2, 'DOCTOR'),\n" +
                    "  (3, 'NURSE'),\n" +
                    "  (4, 'RECEPTIONIST'),\n" +
                    "  (5, 'ACCOUNTANT'),\n" +
                    "  (6, 'ADMIN');");
        }
        insertDepartments();
       if(userRepository.count()==0){
           String hashPassword = passwordEncoder.encode("123456");
           jdbcTemplate.update(
                   "INSERT INTO users (id, fullname, phone_number, password, username, role_id,is_active) " +
                           "VALUES (?, ?, ?, ?, ?, ?,?)",
                   1,
                   "Hoang Duc Admin",
                   "012349998",
                   hashPassword,
                   "hoangducadmin",
                   6,true
           );

       }
    }
    public void insertDepartments() {
        if(departmentRepository.count()==0){
            int id = 1;
            for (DepartmentEnum department : DepartmentEnum.values()) {
                jdbcTemplate.update(
                        "INSERT INTO departments (id, department_name) VALUES (?, ?)",
                        id++,
                        department.name()
                );
            }
        }

    }
}
