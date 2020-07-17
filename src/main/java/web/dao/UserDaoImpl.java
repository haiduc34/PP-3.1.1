package web.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }


    @Override
    public User getUserByName(String name) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("from User where name = :name")
                .setParameter("name", name)
                .setMaxResults(1);

        List<User> users = query.getResultList();
        Iterator<User> it = users.iterator();
        if (!it.hasNext()) {
            return null;
        }
        User user = it.next();
        return user;
    }

    @Override
    public User getUserById(Long id) {
        //Session session = currentSession();
        //session.clear();
        //User user = session.load(User.class, id);
        //return user;

        EntityManager em = getEntityManager();

        Query query = em.createQuery("from User where id = :id")
                .setParameter("id", id)
                .setMaxResults(1);

        List<User> users = query.getResultList();
        Iterator<User> it = users.iterator();
        if (!it.hasNext()) {
            return null;
        }
        User user = it.next();
        return user;
    }


    @Override
    public void addUser (User user) {
        EntityManager em = getEntityManager();
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
            EntityManager em = getEntityManager();
            User changedUser = getUserById(user.getId());
            em.detach(changedUser);
            em.merge(user);
    }

    @Override
    public void removeUser(Long id) {
        EntityManager em = getEntityManager();
        User deletedUser = getUserById(id);
        em.remove(deletedUser);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        EntityManager em = getEntityManager();
        @SuppressWarnings("unchecked")
        List<User> result = em.createQuery("from User").getResultList();
        return result;
    }


    public Role getRoleByName(String name) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("from Role where role = :role")
                .setParameter("role", name)
                .setMaxResults(1);


        Role role = (Role) query.getSingleResult();

        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("from Role where id = :id");
        Role role = (Role) query.getSingleResult();
        return role;
    }

    @Override
    public void addRole(Role role) {
        EntityManager em = getEntityManager();
        em.persist(role);
    }


}
