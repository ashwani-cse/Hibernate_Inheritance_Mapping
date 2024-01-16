package com.inheritence.type.demo.inheritance_strategies.single_table;

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
@DiscriminatorValue(value = "person")
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name = "st_person")
public class Person {

    @Id
    private int id;
    private String name;
}
