package tech.buildrun.springsecurity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValuesTest {

    @Test
    public void testEnumValues() {

        Values adminValue = Values.ADMIN;
        assertNotNull(adminValue);
        assertEquals(1L, adminValue.getRoleId());

        Values basicValue = Values.BASIC;
        assertNotNull(basicValue);
        assertEquals(2L, basicValue.getRoleId());
    }

    @Test
    public void testEnumValueOf() {

        Values adminValue = Values.valueOf("ADMIN");
        assertEquals(Values.ADMIN, adminValue);

        Values basicValue = Values.valueOf("BASIC");
        assertEquals(Values.BASIC, basicValue);
    }

    @Test
    public void testEnumValuesIteration() {

        Values[] allValues = Values.values();
        assertEquals(2, allValues.length);
        assertArrayEquals(new Values[]{Values.ADMIN, Values.BASIC}, allValues);
    }
}
