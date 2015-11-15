package ru.vrn.velichkin.dao;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.Menu_;
import ru.vrn.velichkin.model.Restorant;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.model.Voting;
import ru.vrn.velichkin.model.Voting_;

/**
 * 
 * @author Roman
 */
@Component
@Transactional
public class VotingDao extends AbstractDao<Voting> {
    

    /**
     * Find user's vote for the specified date.
     * @param user
     * @param date
     * @return 
     */
    public Voting findVote(User user, Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Voting> query = cb.createQuery(Voting.class);
        Root<Voting> from = query.from(Voting.class);
        ParameterExpression<Date> dateParam = cb.parameter(Date.class);
        query.where(cb.and(
                cb.equal(from.get(Voting_.user), user),
                cb.equal(from.get(Voting_.date), dateParam)));
        TypedQuery<Voting> q = em.createQuery(query);
        q.setParameter(dateParam, date, TemporalType.DATE);
        try {
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
