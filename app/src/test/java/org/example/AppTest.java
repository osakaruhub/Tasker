/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void appHasAGreeting() {
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test public void commands() {
        assertEquals(client.write(ADD + "Max"));
    }
    @Test public void run() {
    }


}
