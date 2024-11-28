package tech.buildrun.springsecurity.monitorable;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MonitoringAspect {

    private final MeterRegistry meterRegistry;

    public MonitoringAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("@annotation(monitorable)")
    public Object monitorMethod(ProceedingJoinPoint joinPoint, Monitorable monitorable) throws Throwable {
        String metricName = monitorable.value();
        if (metricName.isEmpty()) {
            metricName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        }

        Timer timer = Timer.builder(metricName)
                .description("Métrica personalizada para monitoramento de métodos")
                .register(meterRegistry);

        return timer.record(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}
