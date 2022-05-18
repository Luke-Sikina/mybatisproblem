package com.sikina.mybatisproblem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
// This SQL script will run before every test in this class
@Sql(scripts = {"/data.sql"})
class ExampleMapperIntegrationTest {

    @Container
    static final MySQLContainer<?> databaseContainer = new MySQLContainer<>("mysql:5.7");

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", databaseContainer::getJdbcUrl);
        registry.add("spring.datasource.username", databaseContainer::getUsername);
        registry.add("spring.datasource.password", databaseContainer::getPassword);
    }

    @Autowired
    ExampleMapper exampleMapper;

    @Test
    void shouldGetThings() {
        // works
        List<ExampleObject> actual = exampleMapper.getSomeThingsWorks(List.of(1, 1, 2, 4, 5));
        List<ExampleObject> expected = List.of(ex(1, "foo"), ex(2, "bar"), ex(5, "baz"));
        Assertions.assertEquals(expected, actual);

        // fails
        actual = exampleMapper.getSomeThingsBreaks(List.of(1, 1, 2, 4, 5));
        expected = List.of(ex(1, "foo"), ex(2, "bar"), ex(5, "baz"));
        Assertions.assertEquals(expected, actual);
    }

    private ExampleObject ex(int id, String name) {
        ExampleObject e = new ExampleObject();
        e.setId(id);
        e.setName(name);
        return e;
    }
}