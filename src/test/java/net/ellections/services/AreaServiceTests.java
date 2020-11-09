package net.ellections.services;

import java.util.Arrays;
import java.util.List;
import net.ellections.entities.Area;
import net.ellections.reporitories.AreaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTests {

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private AreaService areaService;

    @Test
    public void getAllAreasTest(){
        List<Area> areaList;

        Area area1 = Area.builder().id(1L).area("Motivation").build();
        Area area2 = Area.builder().id(2L).area("Fun").build();
        Area area3 = Area.builder().id(3L).area("TeamPlayer").build();
        Area area4 = Area.builder().id(4L).area("Client satisfaction").build();
        Area area5 = Area.builder().id(5L).area("Technical referent").build();

        areaList = Arrays.asList(area1, area2, area3, area4, area5);

        when(areaRepository.findAll()).thenReturn(areaList);
        Assert.assertEquals(5, areaService.getAreas().size());
        Assert.assertEquals(areaList, areaService.getAreas());
    }
}
