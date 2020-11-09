package net.ellections.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AreaRequest {
    @NotBlank(message = "Area field must be present.")
    private String area;
}
