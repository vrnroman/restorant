package ru.vrn.velichkin.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class MenuDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public Menu findById(Long menuId) {
        return em.find(Menu.class, menuId);
    }
    
    public void save(Menu menu) {
        em.merge(menu);
    }
    
    
    public List<Menu> getAllMenu() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Menu> query = cb.createQuery(Menu.class);
        Root<Menu> from = query.from(Menu.class);
        TypedQuery<Menu> q = em.createQuery(query);
        return q.getResultList();
    }
    
    /**
     * Find restorant's menu for the specified date.
     * @param restorant
     * @param date
     * @return 
     */
    public Menu findMenu(Restorant restorant, Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Menu> query = cb.createQuery(Menu.class);
        Root<Menu> from = query.from(Menu.class);
        ParameterExpression<Date> dateParam = cb.parameter(Date.class);
        query.where(cb.and(
                cb.equal(from.get(Menu_.restorant), restorant),
                cb.equal(from.get(Menu_.actualDate), dateParam)));
        TypedQuery<Menu> q = em.createQuery(query);
        q.setParameter(dateParam, date, TemporalType.DATE);
        try {
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
