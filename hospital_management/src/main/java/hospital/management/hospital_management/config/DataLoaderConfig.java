package hospital.management.hospital_management.config;


import hospital.management.hospital_management.repository.*;
import hospital.management.hospital_management.util.constant.RoleEnum;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public void run(String... args) throws Exception {
        insertRoles();
        insertDepartments();
        insertAdmin();

    }

    public void insertRoles() {
        if(roleRepository.count()==0){
            int id = 1;
            for (RoleEnum role : RoleEnum.values()) {
                jdbcTemplate.update("INSERT INTO roles (id, role_name) VALUES (?, ?)", id++, role.name());
            }
        }

    }

    public void insertAdmin(){
        if (userRepository.count() == 0) {
            String hashPassword = passwordEncoder.encode("123456");
            jdbcTemplate.update(
                    "INSERT INTO users (id, fullname, phone_number, password, username, role_id,is_active) " +
                            "VALUES (?, ?, ?, ?, ?, ?,?)",
                    1,
                    "Hoang Duc Admin",
                    "012349998",
                    hashPassword,
                    "hoangducadmin",
                    6, true
            );
        }
    }

    public void insertDepartments() {
        if (departmentRepository.count() == 0) {
            jdbcTemplate.update(
                    "INSERT INTO Departments (id, department_name, description)\n" +
                            "VALUES\n" +
                            "  (1, 'Emergency', 'Provides immediate treatment for acute illnesses and injuries.'),\n" +
                            "  (2, 'Cardiology', 'Specializes in heart-related diseases and treatments.'),\n" +
                            "  (3, 'Neurology', 'Deals with disorders of the nervous system.'),\n" +
                            "  (4, 'Oncology', 'Treats cancer patients through chemotherapy, radiation, and more.'),\n" +
                            "  (5, 'Pediatrics', 'Provides medical care for infants, children, and adolescents.'),\n" +
                            "  (6, 'Orthopedics', 'Focuses on the musculoskeletal system – bones, joints, and muscles.'),\n" +
                            "  (7, 'Radiology', 'Conducts medical imaging like X-rays, MRIs, and CT scans.'),\n" +
                            "  (8, 'Maternity', 'Handles childbirth and care for mothers and newborns.'),\n" +
                            "  (9, 'Pharmacy', 'Manages medication dispensing and pharmaceutical care.'),\n" +
                            "  (10, 'Pathology', 'Analyzes lab samples to diagnose diseases.');\n"

                    );
        }
    }

}
