package com.inheritence.type.demo.fetch_types.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ashwani Kumar
 * Created on 16/01/24.
 */

@Setter
@Getter
@Entity
public class Product extends Base{
    private String name;
    private Double price;
    //default fetch type for object is EAGER here
    @ManyToOne
    private Category category;
}
