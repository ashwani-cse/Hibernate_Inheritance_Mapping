package com.inheritence.type.demo.inheritance_strategies.mapped_super_class;

import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@Entity(name = "ms_employee")
public class Employee extends  Person{

    private double salary;
}
