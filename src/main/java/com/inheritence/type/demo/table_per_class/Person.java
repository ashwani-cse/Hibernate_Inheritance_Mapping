package com.inheritence.type.demo.table_per_class;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 */

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity(name = "tpc_person")
public class Person {

    @Id
    private int id;
    private String name;
}
