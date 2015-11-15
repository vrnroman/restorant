package ru.vrn.velichkin.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.UsernamePasswordCredentials;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vrn.velichkin.dao.UserDao;
import ru.vrn.velichkin.model.Role;
import ru.vrn.velichkin.model.User;

/**
 *
 * @author Roman
 */
@Component
@Transactional
public class UserService {
    
    private static final String AUTH_SECRET_KEY = "hello,world!";
    
    public static final String HEADER_SECURITY_TOKEN = "X-Auth-Token";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return userDao.findByName(user.getName());
        }
        return null;
    }

    public boolean isAtLeastOneUserExists() {
        return userDao.usersCount() > 0;
    }

    public String createToken(String userName, String password, long expiration) {        
//        try {
            String decodedPassword = password; /*new String(Base64.getDecoder().decode(password), "UTF-8");*/
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                    userName, decodedPassword);
            User user = userDao.findByName(userName);
            if (user != null) {
                if (user.getName().equals(credentials.getUserName()) && user.getPassword().equals(credentials.getPassword())) {
                    long nowMillis = System.currentTimeMillis();
                    Date now = new Date(nowMillis);
                    Date exp = new Date(nowMillis + expiration);

                    return Jwts.builder()
                            .setIssuedAt(now)
                            .setExpiration(exp)
                            .setSubject(userName)
                            .signWith(SignatureAlgorithm.HS512, AUTH_SECRET_KEY)
                            .compact();
                }
            } else {
                throw new RuntimeException("Unknown user");
            }
//        } catch (UnsupportedEncodingException e) {
//            //log
//        }
        return "";
    }


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


    public User parseToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(AUTH_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userDao.findByName(userName);
    }
    
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
