package ru.nekrasov.lr8.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nekrasov.lr8.dto.UserDto;
import ru.nekrasov.lr8.entity.Role;
import ru.nekrasov.lr8.entity.User;
import ru.nekrasov.lr8.respository.RoleRepository;
import ru.nekrasov.lr8.respository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role roleAdmin = checkRoleExist("ROLE_ADMIN");
        Role roleReadonly = checkRoleExist("ROLE_READONLY");
        Role roleManager = checkRoleExist("ROLE_MANAGER");
        Role roleAdministrator = checkRoleExist("ROLE_ADMINISTRATOR");

        if (userRepository.count() == 0) {
            user.setRoles(Arrays.asList(roleAdmin));
            user.setPosition("Владелец");
        } else {
            user.setRoles(Arrays.asList(roleReadonly));
            if (user.getRoles().contains(roleManager)) {
                user.setPosition("Менеджер");
            } else if (user.getRoles().contains(roleAdministrator)) {
                user.setPosition("Администратор");
            } else {
                user.setPosition("Подтвердить регистрацию");
            }
        }

        userRepository.save(user);
    }
    @Override
    public User findUserByEmail(String email){ return userRepository.findByEmail(email);}
    @Override
    public List<UserDto> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user)-> mapToUserDto(user))
                .collect(Collectors.toList());

    }
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setPosition(user.getPosition());
        return userDto;
    }

    private Role checkRoleExist(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }


    public void setNewRoles(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            Role role = roleRepository.findByName(roleName);

            if (role != null) {
                user.getRoles().clear();
                user.getRoles().add(role);
                userRepository.save(user);
            }
        }
    }

}
