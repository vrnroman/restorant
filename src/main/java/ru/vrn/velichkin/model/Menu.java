package ru.vrn.velichkin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RSTR_MENU")
public class Menu extends AbstractEntity {

    @Column(name = "ACTUAL_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date actualDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "RSTR_MENU_TO_MENU_ITEM",
            joinColumns = @JoinColumn(name = "MENU_ID"),
            inverseJoinColumns = @JoinColumn(name = "MENU_ITEM_ID"))
    private List<MenuItem> items = new ArrayList<MenuItem>();

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "RESTORANT_ID")
    private Restorant restorant;
    
    public static final Menu build(Date actualDate, List<MenuItem> items, Restorant restorant) {
        Menu m = new Menu();
        m.actualDate = actualDate;
        m.items = items;
        m.restorant = restorant;
        return m;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public Restorant getRestorant() {
        return restorant;
    }

    public void setRestorant(Restorant restorant) {
        this.restorant = restorant;
    }

}
