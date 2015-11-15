package ru.vrn.velichkin.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.model.User_;

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class UserDao extends AbstractDao<User>{
    
    public long usersCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> from = query.from(User.class);
        query.select(cb.count(from));
        TypedQuery<Long> q = em.createQuery(query);
        return q.getSingleResult();        
    }
    
    public User findByName(String userName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(cb.equal(from.get(User_.name), userName));
        TypedQuery<User> q = em.createQuery(query);
        try {
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
