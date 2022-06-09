package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.model.User;
import mate.academy.spring.model.dto.UserResponseDto;
import mate.academy.spring.service.UserService;
import mate.academy.spring.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll()
                .stream().map(userDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        return userDtoMapper.mapToDto(userService.get(userId));
    }

    @GetMapping("/inject")
    public String inject() {
        User johnDoe = new User();
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");

        User emilyStone = new User();
        emilyStone.setFirstName("Emily");
        emilyStone.setLastName("Stone");

        User hughDane = new User();
        hughDane.setFirstName("Hugh");
        hughDane.setLastName("Dane");

        userService.add(johnDoe);
        userService.add(emilyStone);
        userService.add(hughDane);
        return "Users are injected!";
    }
}