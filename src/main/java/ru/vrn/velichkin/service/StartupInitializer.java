package ru.vrn.velichkin.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.vrn.velichkin.dao.DatabaseFiller;

/**
 * Singleton service, which fires at application startup 
 * and using for filling DB with test data.
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
