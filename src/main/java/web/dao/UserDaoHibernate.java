package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoHibernate implements UserDao{

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public void add(User user) {
        user.setActive(true);
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User userByID(long id) {
        return sessionFactory.getCurrentSession().find(User.class, id);
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
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}
