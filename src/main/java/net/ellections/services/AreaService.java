package net.ellections.services;

import java.util.List;
import lombok.AllArgsConstructor;
import net.ellections.entities.Area;
import net.ellections.reporitories.AreaRepository;
import net.ellections.requests.AreaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AreaService {

    @Autowired
    private final AreaRepository areaRepository;

    public List<Area> getAreas(){

        return areaRepository.findAll();
    }
    public Area createArea(AreaRequest request){

        Area area = Area.builder().area(request.getArea()).build();
        return areaRepository.save(area);

    }
}
