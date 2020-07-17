package web.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        return userDao.getUserByName(s);
    }

    @Transactional
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    public Role getRoleById(Long id) {
        return userDao.getRoleById(id);
    }

    @Transactional
    public Role getRoleByName(String name) {
        if (userDao.getRoleByName(name) == null)
            userDao.addRole(new Role(name));

        return userDao.getRoleByName(name);
    }

}
