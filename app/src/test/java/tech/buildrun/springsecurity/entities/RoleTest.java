package tech.buildrun.springsecurity.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    public void testRoleSettersAndGetters() {
        Role role = new Role();
        role.setRoleId(1L);
        role.setName("ROLE_USER");

        assertEquals(1L, role.getRoleId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    public void testRoleEqualsAndHashCode() {
        Role role1 = new Role();
        role1.setRoleId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setRoleId(1L);
        role2.setName("ROLE_USER");

        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }
}
