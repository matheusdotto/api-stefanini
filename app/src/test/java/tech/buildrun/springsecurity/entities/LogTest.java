package tech.buildrun.springsecurity.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogTest {

    @Test
    public void testLogSettersAndGetters() {

        Log log = new Log();
        log.setId(1L);

        assertEquals(1L, log.getId());
    }

    @Test
    public void testLogEqualsAndHashCode() {

        Log log1 = new Log();
        log1.setId(1L);

        Log log2 = new Log();
        log2.setId(1L);

        assertEquals(log1, log2);
        assertEquals(log1.hashCode(), log2.hashCode());
    }
}
