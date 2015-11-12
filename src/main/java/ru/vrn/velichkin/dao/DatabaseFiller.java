/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.vrn.velichkin.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.MenuItem;
import ru.vrn.velichkin.model.Restorant;

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class DatabaseFiller {
    
    @PersistenceContext
    private EntityManager em;
    
    public void loadTestData() {
        Restorant r1 = Restorant.build("First rstr");
        r1 = em.merge(r1);
        Restorant r2 = Restorant.build("Second rstr");
        r2 = em.merge(r2);
        
        
        int number = 0;
        List<MenuItem> items = new ArrayList<MenuItem>();
        for (int i = 0; i < 20; i++) {
            MenuItem menuItem = MenuItem.build("Dish #" + ++number, BigDecimal.valueOf(number));
            items.add(menuItem);
        }
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 20; i = i + 5) {
            Menu m = Menu.build(c.getTime(), items.subList(i, i + 5), (i % 2 == 0 ? r1 : r2));
            if (i % 2 != 0) {
                c.add(Calendar.DATE, 1);
            }
            em.merge(m);
        }
    }
    
    
}
