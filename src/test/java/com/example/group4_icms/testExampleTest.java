package com.example.group4_icms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class testExampleTest {

    @BeforeEach
    void setUp() {
        System.out.println("before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
    }

    @Test
    void add() {
        assertAll(() -> assertEquals(testExample.add(1,1),2),
                () -> assertEquals(testExample.add(2,3),5)
                );
    }
}