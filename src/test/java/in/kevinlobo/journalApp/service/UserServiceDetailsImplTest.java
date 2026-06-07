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
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceDetailsImplTest {

    @InjectMocks
    private UserServiceDetailsImpl userServiceDetailsImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(UserDetails.builder().username("kevin").password("kevin").roles().build());
        UserDetails userDetails = userServiceDetailsImpl.loadUserByUsername("kevin");
        Assertions.assertNotNull(userDetails);
    }
}
