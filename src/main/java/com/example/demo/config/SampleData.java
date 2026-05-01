package com.example.demo.config;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SampleData {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    @PostConstruct
    public void init() {
        if (customerRepository.count() >= 2) return;

        Division division;
        if (divisionRepository.count() == 0) {
            division = new Division();
            division.setDivision_name("Default");
            division.setCreate_date(new Date());
            division.setLast_update(new Date());
            divisionRepository.save(division);
        } else {
            division = divisionRepository.findAll().get(0);
        }

        Customer c1 = new Customer();
        c1.setFirstName("Amy");
        c1.setLastName("Barber");
        c1.setAddress("137 Rivers St");
        c1.setPostal_code("28445");
        c1.setPhone("3361234567");
        c1.setDivision(division);
        c1.setCreate_date(new Date());
        c1.setLast_update(new Date());

        Customer c2 = new Customer();
        c2.setFirstName("Ben");
        c2.setLastName("Stiller");
        c2.setAddress("148 Oak St");
        c2.setPostal_code("35489");
        c2.setPhone("3369876543");
        c2.setDivision(division);
        c2.setCreate_date(new Date());
        c2.setLast_update(new Date());

        Customer c3 = new Customer();
        c3.setFirstName("Kelly");
        c3.setLastName("Davis");
        c3.setAddress("147 Lake Ave");
        c3.setPostal_code("14752");
        c3.setPhone("1456875421");
        c3.setDivision(division);
        c3.setCreate_date(new Date());
        c3.setLast_update(new Date());

        Customer c4 = new Customer();
        c4.setFirstName("Carter");
        c4.setLastName("Smith");
        c4.setAddress("187 Ginger Rd");
        c4.setPostal_code("65485");
        c4.setPhone("5438524568");
        c4.setDivision(division);
        c4.setCreate_date(new Date());
        c4.setLast_update(new Date());

        Customer c5 = new Customer();
        c5.setFirstName("Ellie");
        c5.setLastName("Wilson");
        c5.setAddress("199 Keys St");
        c5.setPostal_code("98562");
        c5.setPhone("6438214568");
        c5.setDivision(division);
        c5.setCreate_date(new Date());
        c5.setLast_update(new Date());

        customerRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
    }
}
