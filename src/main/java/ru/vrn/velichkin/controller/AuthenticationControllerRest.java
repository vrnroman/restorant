package ru.vrn.velichkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vrn.velichkin.dto.UserDTO;
import ru.vrn.velichkin.service.UserService;

/**
 *
 * @author Roman
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationControllerRest {

    /**
     * token's life time
     */
    private static final long TOKEN_LIFE_TIME = 10 * 60 * 60 * 1000; //10 hours

    /**
     * ������ ��� ������ � ������������.
     */
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String getAuthToken(@RequestBody UserDTO userDTO) {
        return userService.createToken(userDTO.getName(), userDTO.getPassword(), TOKEN_LIFE_TIME);
    }
}
