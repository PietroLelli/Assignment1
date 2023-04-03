package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("Bob", 33);
    }

    @Test
    void testGetName() {
        assertEquals("Bob", person.getName());
    }

    @Test
    void testUpdateAge() {
        assertEquals(33, person.getAge());
        person.updateAge(34);
        assertEquals(34, person.getAge());
    }

    @Test
    void testUpdateAge2() {
        person.updateAge(5);
        assertEquals(5, person.getAge());
    }
}