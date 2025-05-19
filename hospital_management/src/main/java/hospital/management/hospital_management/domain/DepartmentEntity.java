package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.DepartmentEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    DepartmentEnum departmentName;

//    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
//    List<DoctorEntity> doctors;

}
