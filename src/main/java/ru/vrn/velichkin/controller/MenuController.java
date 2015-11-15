package ru.vrn.velichkin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vrn.velichkin.model.Menu;
import ru.vrn.velichkin.model.Role;
import ru.vrn.velichkin.model.User;
import ru.vrn.velichkin.service.MenuService;
import ru.vrn.velichkin.service.UserService;
import ru.vrn.velichkin.service.VotingService;

/**
 *
 * @author Roman
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VotingService votingService;
    
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody()
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }
    
    
    @RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Void> saveMenu(@RequestBody Menu menu, @RequestHeader(UserService.HEADER_SECURITY_TOKEN) String token) {
        User user = userService.parseToken(token);
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (userService.hasRole(user, Role.ROLE_ADMIN_CODE)) {
            menuService.save(menu);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/vote/{menu_id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Void> vote(@PathVariable("menu_id") Long menuId, @RequestHeader(UserService.HEADER_SECURITY_TOKEN) String token) {
        User user = userService.parseToken(token);
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (userService.hasRole(user, Role.ROLE_USER_CODE)) {
            if (votingService.vote(menuId, user)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
    
}
