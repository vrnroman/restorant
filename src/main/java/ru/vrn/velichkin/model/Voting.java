package ru.vrn.velichkin.model;


import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RSTR_VOTING")
public class Voting extends AbstractEntity {

    @ManyToOne(optional = false, cascade = {})
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @ManyToOne(optional = false, cascade = {})
    @JoinColumn(name = "RESTORANT_ID")
    private Restorant restorant;
    
    @Column(name = "VOTING_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    public static final Voting build(User user, Restorant restorant, Date date) {
        Voting voting = new Voting();
        voting.date = date;
        voting.restorant = restorant;
        voting.user = user;
        return voting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restorant getRestorant() {
        return restorant;
    }

    public void setRestorant(Restorant restorant) {
        this.restorant = restorant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    
    
}
