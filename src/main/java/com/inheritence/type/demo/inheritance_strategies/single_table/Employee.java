package com.inheritence.type.demo.inheritance_strategies.single_table;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@DiscriminatorValue(value = "employee")
@Entity
public class Employee extends Person {

    private double salary;
}
