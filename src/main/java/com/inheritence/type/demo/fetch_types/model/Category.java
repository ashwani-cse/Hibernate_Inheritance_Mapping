package com.inheritence.type.demo.fetch_types.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 16/01/24.
 */

@Setter
@Getter
@Entity
public class Category extends Base{

    private String name;
    //default fetch type collection is LAZY here
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER) // if we don't use mapping, application will give exception during start like product is not bind properly
    private List<Product> products;
}
