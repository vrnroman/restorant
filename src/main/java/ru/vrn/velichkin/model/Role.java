package ru.vrn.velichkin.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RSTR_ROLE")
public class Role extends AbstractEntity {
    
    public static final String ROLE_ADMIN_CODE = "ROLE_ADMIN_CODE";
    
    public static final String ROLE_USER_CODE = "ROLE_USER_CODE";

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
