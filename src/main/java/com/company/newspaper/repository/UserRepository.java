package com.company.newspaper.repository;

import com.company.newspaper.model.enums.Role;
import com.company.newspaper.model.web.RegistrationRequest;
import com.company.newspaper.model.entities.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private static Map<String, User> users = new HashMap<>(); //username, user

    private static Set<User> admins = new HashSet<>();

    public User createUser(RegistrationRequest request) {
        User mapUser = users.get(request.getUsername());
        if (mapUser != null) {
            throw new RuntimeException("User already exists");
        }

        User user =  User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(request.getPassword())
                .id(users.size() + 1)
                .build();

        List<Role> roles;
        if (users.size() == 0) {
            roles = Role.ADMIN.getIncludedRoles();
            admins.add(user);
        } else {
            roles = Role.READER.getIncludedRoles();
        }
        user.setRoles(roles);

        users.putIfAbsent(user.getUsername(), user);
        return user;
    }

    public User getByUsernameAndPassword(@NonNull String username,
                                         @NonNull String password) {
        User user = users.get(username);

        if ((user != null) && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getByUsername(@NonNull String username) {
        return users.get(username);
    }

    public User getByUserId(Integer id) {
        return users.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User promote(String username, Integer roleIndex) {

        User user = users.get(username);

        Role role = Role.getByIndex(roleIndex);
        if (role == null) {
            throw new IndexOutOfBoundsException("Wrong role index");
        }

        if (user.getRoles().contains(role)) {
            throw new RuntimeException("User already has role. Promotion impossible.");
        }

        user.setRoles(role.getIncludedRoles());

        return user;
    }

    public User demote(String username, Integer roleIndex) {

        User user = users.get(username);
        if (user == null) {
            throw new RuntimeException(String.format("User %s not found.", username));
        }

        Role role = Role.getByIndex(roleIndex);
        if (role == null) {
            throw new IndexOutOfBoundsException("Wrong role index");
        }

        if (!user.getRoles().contains(role)) {
            throw new RuntimeException("User already doesn't have role. Demotion impossible.");
        }

        if (user.getRoles().contains(Role.ADMIN)
                && role.getIndex() < Role.ADMIN.getIndex()
                && admins.size() == 1) {
            throw new RuntimeException("User is last admin. Demotion impossible.");
        }

        user.setRoles(role.getIncludedRoles());

        return user;
    }
}
