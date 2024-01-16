package com.inheritence.type.demo.inheritance_strategies.joined_table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ashwani Kumar
 * Created on 13/01/24.
 * <BR/><BR/>
 * A mapped superclass has no separate table defined for it.
 * A class designated with the MappedSuperclass annotation can be mapped
 * in the same way as an entity except that the mappings will apply only
 * to its subclasses since no table exists for the mapped superclass itself.
 *
 * <BR/><BR/>
 * <b>Note:</b> Getter/ Setter required here beacuse how child will use the id field for their mappings.
 */

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "jt_person")
public class Person {

    @Id
    private int id;
    private String name;
}
