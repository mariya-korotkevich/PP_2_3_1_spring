package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoHibernate implements UserDao{

    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public UserDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();

    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public void add(User user) {
        user.setActive(true);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User userByID(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(long id, User user) {
        User oldUser = userByID(id);
        oldUser.setActive(user.isActive());
        oldUser.setName(user.getName());
        oldUser.setCity(user.getCity());
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
    }

    @Override
    public void delete(long id) {
        User user = userByID(id);
        if (user != null){
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }
}
