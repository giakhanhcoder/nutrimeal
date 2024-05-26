package com.nutrimeal.nutrimeal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private int dishId;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "dish_description")
    private String dishDescription;

    @Column(name = "dish_image")
    private String dishImage;

    @Column(name = "dish_proteins")
    private int dishProteins;

    @Column(name = "dish_fats")
    private int dishFats;

    @Column(name = "dish_carb")
    private int dishCarbs;

    @Column(name = "dish_calories")
    private int dishCalories;

    @Column(name = "dish_type")
    private String dishType;
}