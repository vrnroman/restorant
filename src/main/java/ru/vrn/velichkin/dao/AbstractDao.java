package ru.vrn.velichkin.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import ru.vrn.velichkin.model.AbstractEntity;

/**
 * Abstract Dao with simple methods.
 * @author Roman
 */
@Component
public abstract class AbstractDao<E extends AbstractEntity> {
    
    private Class<E> entityClass;
    
    @PersistenceContext
    protected EntityManager em;
    
    public E findById(Long id) {
        return em.find(getEntityClass(), id);
    }
    
    public E save(E entity) {
        return em.merge(entity);
    }
    
    public List<E> getAllEntities() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(getEntityClass());
        Root<E> from = query.from(getEntityClass());
        TypedQuery<E> q = em.createQuery(query);
        return q.getResultList();
    }
    
    /**
     * Get generic Class from concrete Dao.
     * @return Entity class.
     */
    protected Class<E> getEntityClass() {
        if (entityClass == null) {
            Class<?> actualClass = getClass();
            Class<?> superClass = actualClass.getSuperclass();
            while (!AbstractDao.class.equals(superClass)) {
                actualClass = superClass;
                superClass = superClass.getSuperclass();
            }
            entityClass = (Class) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }
    
}
