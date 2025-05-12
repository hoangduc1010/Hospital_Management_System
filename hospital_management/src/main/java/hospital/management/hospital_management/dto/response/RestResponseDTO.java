package hospital.management.hospital_management.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponseDTO<T> {
    private int statusCode;

    private String errorMessage;

    private Object message;

    private T data;
}
