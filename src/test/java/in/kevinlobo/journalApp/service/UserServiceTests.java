package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;  // Mock — no real DB call

    @InjectMocks
    private UserService userService;  // Injects the mock userRepository into UserService

    private User user1;
    private User user2;

//    @BeforeEach
//    void setUp() {
//        user1 = new User("kevin", "kevin");
//        user1.setRoles(Arrays.asList("USER"));
//
//        user2 = new User("john", "kevin");
//        user2.setRoles(Arrays.asList("USER"));
//    }

    @Test
    void getAllUsers_ShouldReturnAllUsers_WhenUsersExist() {
        // Arrange — tell mock what to return
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        // Act
        List<User> result = userService.getAllUsers();
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("kevin", result.get(0).getUserName());
        Assertions.assertEquals("john", result.get(1).getUserName());

        // Verify findAll() was called exactly once
        verify(userRepository, times(1)).findAll();
    }

    // ✅ Test 2: Returns empty list when no users exist
    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findAll();
    }

    // ✅ Test 3: Returns single user list correctly
    @Test
    void getAllUsers_ShouldReturnSingleUser_WhenOnlyOneUserExists() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user1));

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("kevin", result.get(0).getUserName());
        Assertions.assertEquals("USER", result.get(0).getRoles().get(0));
        Assertions.assertEquals("kevin", result.get(0).getPassword());
    }

    // ✅ Test 4: Returned list matches exactly what repository returns
    @Test
    void getAllUsers_ShouldReturnExactRepositoryResult() {
        List<User> mockUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(mockUsers);
        // Act
        List<User> result = userService.getAllUsers();
        // Assert — result must be the exact same list returned by repo
        Assertions.assertEquals(mockUsers, result);
    }

    // ✅ Test 5: findAll() is called exactly once (no extra calls)
    @Test
    void getAllUsers_ShouldCallFindAllExactlyOnce() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1));
        // Act
        userService.getAllUsers();
        // Assert — verify no extra/unnecessary repository calls
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}
