package com.step.hryshkin.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(
            name = "ORDERS_GOODS",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GOOD_ID")
    )
    private List<Good> goods = new ArrayList<>();

    public Order() {
    }

    public Order(User user, BigDecimal totalPrice, List<Good> goods) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.goods = goods;
    }

    public Order(User user, BigDecimal totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.goods = new ArrayList<>();
    }

    public Order(Long id, BigDecimal totalPrice, User user, List<Good> goods) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.user = user;
        this.goods = goods;
    }

    public void addGood(Good good) {
        if (this.goods == null) this.goods = new ArrayList<>();
        this.goods.add(good);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(totalPrice, order.totalPrice)
                && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                ", goods=" + goods +
                '}';
    }
}
