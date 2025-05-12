package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullname;

    String username;

    String password;

    String phoneNumber;

    String dob;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    @ManyToOne
    @JoinColumn(name="role_id")
    RoleEntity role;



}
