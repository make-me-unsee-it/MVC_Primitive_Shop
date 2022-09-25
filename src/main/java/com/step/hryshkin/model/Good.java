package com.step.hryshkin.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "GOODS")
public class Good implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    public Good(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Good(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public Good() {
    }

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", user=" + user +
                ", order=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Good)) return false;
        Good good = (Good) o;
        return Objects.equals(id, good.id) && Objects.equals(title, good.title) && Objects.equals(price, good.price) && Objects.equals(user, good.user) && Objects.equals(order, good.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, user, order);
    }
}

