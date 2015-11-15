
package ru.vrn.velichkin.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.dao.MenuDao;
import ru.vrn.velichkin.dao.VotingDao;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.Restorant;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.model.Voting;
import ru.vrn.velichkin.utils.DateUtils;

/**
 * Service for voting operation.
 * 
 * @author Roman
 */
@Component
@Transactional
public class VotingService {

    /**
     * Can't vote after this time (in hours);
     */
    private static final int DEADLINE = 11;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private VotingDao votingDao;
    
    /**
     * User want to vote for the menu.
     * @param menuId
     * @param user
     * @return true - when voting is successfull.
     */
    public boolean vote(Long menuId, User user) {
        Menu menu = menuDao.findById(menuId);
        if (menu == null) {
            return false;
        }
        return vote(menu.getRestorant(), user, menu.getActualDate(), true);
    }

    //should be private
    public boolean vote(Restorant restorant, User user, Date date, boolean shouldValidate) {
        try {
            if (!shouldValidate || validate(restorant, user, date)) {
                Voting voting = votingDao.findVote(user, date);
                if (voting == null) {
                    voting = Voting.build(user, restorant, date);
                } else {
                    voting.setRestorant(restorant);
                }
                votingDao.save(voting);
                return true;
            }
            return false;
        } catch (RuntimeException ex) {
            //log
            return false;
        }
    }

    private boolean validate(Restorant restorant, User user, Date date) {
        //Can't vote for past time
        if (DateUtils.isDateInPast(date)) {
            return false;
        }
        //Can't vote for the restorant without menu
        Menu menu = menuDao.findMenu(restorant, date);
        if (menu == null) {
            return false;
        }
        //Can't vote for the restorant twice after deadline time (11 A.M.)
        Voting voting = votingDao.findVote(user, date);
        if (voting != null && isAfterDeadline() && 
                org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(), date)) {
            return false;
        }
        //all ok
        return true;
    }


    /**
     * Testing: is current time after deadline time?
     * @return 
     */
    private boolean isAfterDeadline() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= DEADLINE;
    }

}
