package net.ellections.services;

import java.util.Arrays;
import java.util.List;
import net.ellections.entities.Role;
import net.ellections.reporitories.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void getAllRolesTest(){
        List<Role> roleList;

        Role role1 = Role.builder().id(1L).name("ADMIN").build();
        Role role2 = Role.builder().id(2L).name("EMPLOYEE").build();

        roleList = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roleList);
        Assert.assertEquals(2, roleService.getRoles().size());
        Assert.assertEquals(roleList, roleService.getRoles());
    }

}
