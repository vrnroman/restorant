package ru.vrn.velichkin.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.MenuItem;
import ru.vrn.velichkin.model.Restorant;
import ru.vrn.velichkin.model.Role;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.model.Voting;
import ru.vrn.velichkin.service.UserService;
import ru.vrn.velichkin.service.VotingService;

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class DatabaseFiller {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VotingService votingService;
    
    @Autowired
    private UserService userService;
    
    private boolean isDataExists() {
        return userService.isAtLeastOneUserExists();
    }
    
    public void saveTestData() {
        //shouldn't create when data allready exists
        if (isDataExists()) {
            return;
        }
        //restorant
        Restorant r1 = Restorant.build("First rstr");
        r1 = em.merge(r1);
        Restorant r2 = Restorant.build("Second rstr");
        r2 = em.merge(r2);
        
        //menu item
        int number = 0;
        List<MenuItem> items = new ArrayList<MenuItem>();
        for (int i = 0; i < 20; i++) {
            MenuItem menuItem = MenuItem.build("Dish #" + ++number, BigDecimal.valueOf(number));
            items.add(menuItem);
        }
        
        //menu
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 20; i = i + 5) {
            Menu m = Menu.build(c.getTime(), items.subList(i, i + 5), (i % 2 == 0 ? r1 : r2));
            if (i % 2 != 0) {
                c.add(Calendar.DATE, 1);
            }
            em.merge(m);
        }
        
        //role
        Role userRole = Role.build(Role.ROLE_USER_CODE);
        userRole = em.merge(userRole);
        Role adminRole = Role.build(Role.ROLE_ADMIN_CODE);
        adminRole = em.merge(adminRole);
        
        //user
        User admin = User.build("admin", "admin", Arrays.asList(adminRole));
        User superUser = User.build("super", "super", Arrays.asList(adminRole, userRole));
        User user1 = User.build("user1", "user1", Arrays.asList(userRole));
        User user2 = User.build("user2", "user2", Arrays.asList(userRole));
        admin = em.merge(admin);
        superUser = em.merge(superUser);
        user1 = em.merge(user1);
        user2 = em.merge(user2);
        
        //voting
        votingService.vote(r1, user1, new Date(), false);
        votingService.vote(r2, user1, new Date(), false);
        votingService.vote(r2, user2, new Date(), false);
        votingService.vote(r1, user1, c.getTime(), false);
    }
    
    
}
