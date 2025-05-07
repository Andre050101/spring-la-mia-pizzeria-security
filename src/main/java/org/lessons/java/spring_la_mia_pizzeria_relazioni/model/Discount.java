package org.lessons.java.spring_la_mia_pizzeria_relazioni.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Discount date beginning must be not null!")
    private LocalDate discountDateBegin;

    @NotNull(message = "Discount date end must be not null!")
    private LocalDate discountDateEnd;

    @NotBlank(message = "Title is required!")
    private String title;

    @ManyToOne
    @JoinColumn(name = "pizzaId", nullable = false)
    private Pizza pizza;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDiscountDateBegin() {
        return discountDateBegin;
    }

    public void setDiscountDateBegin(LocalDate discountDateBegin) {
        this.discountDateBegin = discountDateBegin;
    }

    public LocalDate getDiscountDateEnd() {
        return discountDateEnd;
    }

    public void setDiscountDateEnd(LocalDate discountDateEnd) {
        this.discountDateEnd = discountDateEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

}
