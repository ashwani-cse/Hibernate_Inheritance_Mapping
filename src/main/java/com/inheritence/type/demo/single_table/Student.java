package com.inheritence.type.demo.single_table;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@DiscriminatorValue(value = "student")
@Entity
public class Student extends Person {

    private String course;
}
