package org.example.jacoco.repro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassToTestIntegrationTest {

    ClassToTest underTest = new ClassToTest();
    
    @Test
    void test_resultWithoutAction() {
        assertThrows(IllegalArgumentException.class,
                () -> underTest.result("", null));
    }
    
    @Test
    void test_resultConcat() {
        assertEquals("iCONCAT",
                underTest.result("i",
                        ClassToTest.Action.CONCAT));
    }

    @Test
    void test_resultLENGTH() {
        assertEquals("1",
                underTest.result("i",
                        ClassToTest.Action.LENGTH));
    }
}
