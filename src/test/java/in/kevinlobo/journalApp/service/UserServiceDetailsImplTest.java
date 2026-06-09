package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceDetailsImplTest {

    @InjectMocks
    private UserServiceDetailsImpl userServiceDetailsImpl;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest() {
//        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
//                .thenReturn(org.springframework.security.core.userdetails.User.builder().username("kevin").password("kevin").roles().build());
//        UserDetails userDetails = userServiceDetailsImpl.loadUserByUsername("kevin");
//        Assertions.assertNotNull(userDetails);
        User mockUser;
        mockUser = new User();
        mockUser.setUserName("testUser");
        mockUser.setPassword("encodedPassword");
        mockUser.setRoles(List.of("USER", "ADMIN"));

        when(userRepository.findByUserName("testUser")).thenReturn(mockUser);

        // Act
        UserDetails userDetails = userServiceDetailsImpl.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository).findByUserName("testUser");
        verifyNoMoreInteractions(userRepository);
    }
}
