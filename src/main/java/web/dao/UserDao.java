package web.dao;


import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);

    public User getUserById(Long id);

    void addUser(User user);

    public void updateUser(User user);

    public void removeUser(Long id);

    public List<User> listUsers();

    Role getRoleByName(String name);

    void addRole(Role role);

    Role getRoleById(Long id);
}

