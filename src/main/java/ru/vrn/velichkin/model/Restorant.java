package ru.vrn.velichkin.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RSTR_RESTORANT")
public class Restorant extends AbstractEntity {

    @Size(max = 80)
    @Column(name = "NAME", nullable = false)
    private String name;
    
    public static final Restorant build(String name) {
        Restorant r = new Restorant();
        r.name = name;
        return r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
