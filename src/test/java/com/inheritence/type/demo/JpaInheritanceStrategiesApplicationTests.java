package com.inheritence.type.demo;

import com.inheritence.type.demo.inheritance_strategies.mapped_super_class.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:/application.yml")

@SpringBootTest
class JpaInheritanceStrategiesApplicationTests {

    @Mock
    private SessionFactory sessionFactory;

    @Test
    void contextLoads() {
    }

    @Test
    void insertPersons_mappedSuperClass(){
        Employee employee = new Employee();
        employee.setName("Ashwani");
        Session session = sessionFactory.openSession();
        System.out.println("session: "+session);
        session.persist(employee);

    }

}
