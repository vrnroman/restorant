package ru.vrn.velichkin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.dao.MenuDao;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.utils.DateUtils;

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class MenuService {
    
    @Autowired
    private MenuDao menuDao;
    
    public List<Menu> getAllMenu() {
        return menuDao.getAllEntities();
    }
    
    /**
     * Add new menu.
     * @param menu 
     */
    public void save(Menu menu) {
        save(menu, true);
    }
    
    //should be private
    public void save(Menu menu, boolean shouldValidate) {
        if (!shouldValidate || validate(menu)) {
            menuDao.save(menu);
        }
    }
    
    /**
     * Testing: is menu valid for adding?
     * @param menu
     * @return 
     */
    private boolean validate(Menu menu) {
        //date can't be null or in past
        if (menu.getActualDate() == null || DateUtils.isDateInPast(menu.getActualDate())) {
            return false;
        }
        //restorant can't be null
        if (menu.getRestorant() == null) {
            return false;
        }
        //menu must have at least 1 dish
        if (menu.getItems() == null || menu.getItems().size() < 2) {
            return false;
        }            
        //can't change menu (new-only)
        if (menu.getRestorant().getId() != null) {
            Menu oldMenu = menuDao.findMenu(menu.getRestorant(), menu.getActualDate());
            if (oldMenu != null) {
                return false;
            }
        }
        return true;
    }
    
}
