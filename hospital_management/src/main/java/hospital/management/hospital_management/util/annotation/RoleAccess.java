package hospital.management.hospital_management.util.annotation;

import hospital.management.hospital_management.util.constant.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAccess {
    RoleEnum[] allowedRoles();
}