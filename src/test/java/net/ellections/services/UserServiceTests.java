package net.ellections.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.ellections.entities.User;
import net.ellections.reporitories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllUsersTest(){
        List<User> userList;

        User user1 = User.builder().id(1L).createdAt(new Date()).deletedAt(null)
            .name("jperez")
            .email("jperez@avalith.net")
            .password("secret")
            .roles(null)
            .uuid(null)
            .timezone("UTC")
            .build();

        User user2 = User.builder().id(1L).createdAt(new Date()).deletedAt(null)
            .name("pgonzalez")
            .email("pgonzalez@avalith.net")
            .password("secret")
            .roles(null)
            .uuid(null)
            .timezone("UTC")
            .build();

        User user3 = User.builder().id(1L).createdAt(new Date()).deletedAt(null)
            .name("pfernandez")
            .email("pfernandez@avalith.net")
            .password("secret")
            .roles(null)
            .uuid(null)
            .timezone("UTC")
            .build();

        User user4 = User.builder().id(1L).createdAt(new Date()).deletedAt(null)
            .name("rgarcia")
            .email("rgarcia@avalith.net")
            .password("secret")
            .roles(null)
            .uuid(null)
            .timezone("UTC")
            .build();

        User user5 = User.builder().id(1L).createdAt(new Date()).deletedAt(null)
            .name("mmendez")
            .email("mmendez@avalith.net")
            .password("secret")
            .roles(null)
            .uuid(null)
            .timezone("UTC")
            .build();

        userList = Arrays.asList(user1, user2, user3, user4, user5);

        when(userRepository.findAll()).thenReturn(userList);
        Assert.assertEquals(5, userService.getAllUsers().size());
        Assert.assertEquals(userList, userService.getAllUsers());
    }
}
