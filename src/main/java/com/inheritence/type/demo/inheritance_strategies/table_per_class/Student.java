package com.inheritence.type.demo.inheritance_strategies.table_per_class;

import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@Entity(name = "tpc_student")
public class Student extends Person {

    private String course;
}
