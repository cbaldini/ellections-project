package net.ellections.responses;

import lombok.Builder;
import lombok.Data;
import net.ellections.entities.Area;

@Data
@Builder
public class AreaResponse {
    private Long id;
    private String area;

    public static AreaResponse fromEntity(Area area){
        return AreaResponse.builder()
            .id(area.getId())
            .area(area.getArea())
            .build();
    }
}
