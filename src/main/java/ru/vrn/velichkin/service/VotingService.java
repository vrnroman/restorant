/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.vrn.velichkin.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vrn.velichkin.dao.MenuDao;
import ru.vrn.velichkin.dao.VotingDao;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.Restorant;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.model.Voting;

/**
 *
 * @author Roman
 */
@Component
public class VotingService {

    /**
     * Can't vote after this time (in hours);
     */
    private static final int DEADLINE = 11;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private VotingDao votingDao;

    public boolean vote(Restorant restorant, User user) {
        return vote(restorant, user, new Date(), true);
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
        if (isDateInPast(date)) {
            return false;
        }
        //Can't vote for the restorant without menu
        Menu menu = menuDao.findMenu(restorant, date);
        if (menu == null) {
            return false;
        }
        //Can't vote for the restorant twice after deadline time (11 A.M.)
        Voting voting = votingDao.findVote(user, date);
        if (voting != null && isAfterDeadline()) {
            return false;
        }
        //all ok
        return true;
    }

    private boolean isDateInPast(Date date) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, currentDate.getActualMinimum(Calendar.HOUR_OF_DAY));
        currentDate.set(Calendar.MINUTE, currentDate.getActualMinimum(Calendar.MINUTE));
        currentDate.set(Calendar.SECOND, currentDate.getActualMinimum(Calendar.SECOND));
        currentDate.set(Calendar.MILLISECOND, currentDate.getActualMinimum(Calendar.MILLISECOND));
        return !currentDate.after(date);
    }

    private boolean isAfterDeadline() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= DEADLINE;
    }

}
