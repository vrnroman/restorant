package ru.vrn.velichkin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.dao.DatabaseFiller;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.MenuItem;
import ru.vrn.velichkin.model.Restorant;

/**
 *
 * @author Roman
 */
@Service
@Scope(value = "singleton")
public class StartupInitializer {
    
    @Autowired
    private DatabaseFiller databaseFiller;
    
    @PostConstruct
    public void loadTestData() {
        databaseFiller.saveTestData();
    }
    
}
