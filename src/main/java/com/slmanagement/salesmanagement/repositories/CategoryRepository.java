package com.slmanagement.salesmanagement.repositories;

import com.slmanagement.salesmanagement.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Hibernate + Spring JPA made, with the only line below, this:
    // SELECT * FROM CATEGORY WHERE CATEGORY.NAME = NAME
    // more infos here: https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods.query-creation
    Category findByName(String name);
}
