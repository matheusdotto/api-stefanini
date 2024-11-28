package tech.buildrun.springsecurity.monitorable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.mockito.Mockito.mock;

public class MonitorableAspectTest {

    private MonitoringAspect aspect;

    @BeforeEach
    void setUp() {
        aspect = mock(MonitoringAspect.class);
    }

    @Test
    void testMonitorableAnnotation() {

        TestClass target = new TestClass();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(aspect);
        TestClass proxy = factory.getProxy();

        proxy.monitoredMethod();

    }

    // Classe de exemplo para aplicar a anotação
    static class TestClass {
        @Monitorable("testValue")
        public void monitoredMethod() {
            System.out.println("Executing monitored method");
        }
    }
}
