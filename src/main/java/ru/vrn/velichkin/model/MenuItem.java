package ru.vrn.velichkin.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Menu item (dish).
 * 
 * @author Roman
 */
@Entity
@Table(name = "RSTR_MENU_ITEM")
public class MenuItem extends AbstractEntity {

    /**
     * Dish name.
     */
    @Size(max = 80)
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Dish price.
     */
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;
    
    public static final MenuItem build(String name, BigDecimal price) {
        MenuItem item = new MenuItem();
        item.name = name;
        item.price = price;
        return item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
