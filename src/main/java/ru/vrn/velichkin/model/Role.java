package ru.vrn.velichkin.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * User role.
 * 
 * @author Roman
 */
@Entity
@Table(name = "RSTR_ROLE")
public class Role extends AbstractEntity {
    
    /**
     * Code name for Admin role.
     */
    public static final String ROLE_ADMIN_CODE = "ROLE_ADMIN_CODE";
    
    /**
     * Code name for user role.
     */
    public static final String ROLE_USER_CODE = "ROLE_USER_CODE";

    /**
     * Roles code name.
     */
    @Size(max = 80)
    @Column(name = "CODE", nullable = false)
    private String code;
    
    public static final Role build(String code) {
        Role r = new Role();
        r.code = code;
        return r;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
