package ru.vrn.velichkin.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.dao.UserDao;
import ru.vrn.velichkin.model.Role;
import ru.vrn.velichkin.model.User;

/**
 * Operations with users.
 * 
 * @author Roman
 */
@Component
@Transactional
public class UserService {

    /**
     * salt.
     */
    private static final String AUTH_SECRET_KEY = "hello,world!";

    /**
     * Http header name for token.
     */
    public static final String HEADER_SECURITY_TOKEN = "X-Auth-Token";
    
    /**
     * token's life time
     */
    private static final long TOKEN_LIFE_TIME = 10 * 60 * 60 * 1000; //10 hours

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    /**
     * Testing: is users db table not emplty?
     * @return 
     */
    public boolean isAtLeastOneUserExists() {
        return userDao.usersCount() > 0;
    }

    /**
     * Create auth token for user.
     * @param userName
     * @param password
     * @return 
     */
    public String createToken(String userName, String password) {
        String decodedPassword = password; /*new String(Base64.getDecoder().decode(password), "UTF-8");*/
        User user = userDao.findByName(userName);
        if (user != null) {
            if (user.getName().equals(userName) && user.getPassword().equals(decodedPassword)) {
                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                Date exp = new Date(nowMillis + TOKEN_LIFE_TIME);

                return Jwts.builder()
                        .setIssuedAt(now)
                        .setExpiration(exp)
                        .setSubject(userName)
                        .signWith(SignatureAlgorithm.HS512, AUTH_SECRET_KEY)
                        .compact();
            } else {
                throw new RuntimeException("IncorrectPassword");
            }
        } else {
            throw new RuntimeException("Unknown user");
        }
    }

    /**
     * Validate token
     * @param token
     * @return 
     */
    public boolean validate(String token) {
        if (StringUtils.isNotEmpty(token)) {
            try {
                String userName = Jwts.parser()
                        .setSigningKey(AUTH_SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
                User user = userDao.findByName(userName);
                if (user != null) {
                    return true;
                }
            } catch (Exception e) {
                //log invalid token
            }
        }
        return false;
    }

    /**
     * Get User entity from token.
     * @param token
     * @return 
     */
    public User parseToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(AUTH_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userDao.findByName(userName);
    }

    /**
     * Testing: has user a role?
     * @param user
     * @param role
     * @return 
     */
    public boolean hasRole(User user, String role) {
        user = em.find(User.class, user.getId());
        for (Role r : user.getRoles()) {
            if (role.equals(r.getCode())) {
                return true;
            }
        }
        return false;
    }

}
