package ru.vrn.velichkin.model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * User.
 * 
 * @author Roman
 */
@Entity
@Table(name = "RSTR_USER")
public class User extends AbstractEntity {

    /**
     * Username (login). Must be unique.
     */
    @Size(max = 80)
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
    
    /**
     * Password. Should be encrypted in future.
     */
    @Size(max = 80)
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    
    /**
     * Every user could have several roles.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "RSTR_USER_TO_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles = new ArrayList<Role>();
    
    public static final User build(String name, String password, List<Role> roles) {
        User r = new User();
        r.name = name;
        r.password = password;
        r.roles = roles;
        return r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
