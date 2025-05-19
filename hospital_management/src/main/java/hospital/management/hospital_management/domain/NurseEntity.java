package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.DoctorDiplomaEnum;
import hospital.management.hospital_management.util.constant.NurseDiplomaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name="nurses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NurseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    Integer yearOfExperience;

    @NotNull(message = "Bằng cấp không được để trống")
    @Enumerated(EnumType.STRING)
    NurseDiplomaEnum nurseDiploma;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="nurse_department",
            joinColumns = @JoinColumn(name = "nurse_id"),
            inverseJoinColumns = @JoinColumn(name="department_id")
    )
    Set<DepartmentEntity> departments;
}
