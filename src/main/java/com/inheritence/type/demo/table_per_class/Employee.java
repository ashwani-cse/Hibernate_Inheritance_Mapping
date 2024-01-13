package com.inheritence.type.demo.table_per_class;

import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@Entity(name = "tpc_employee")
public class Employee extends Person {

    private double salary;
}
