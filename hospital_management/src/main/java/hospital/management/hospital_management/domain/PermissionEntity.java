package hospital.management.hospital_management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hospital.management.hospital_management.util.constant.HttpMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name="permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String apiPath;

    @Enumerated(EnumType.STRING)
    HttpMethodEnum method;

    @ManyToMany( mappedBy = "permissions")
    @JsonIgnore
    List<RoleEntity> roles;
}
