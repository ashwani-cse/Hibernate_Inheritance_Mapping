package com.inheritence.type.demo.mapped_super_class;

import jakarta.persistence.Entity;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@Entity(name = "ms_student")
public class Student extends  Person{

    private String course;
}
