package br.com.cinecidade.cinecidade_api.infrastructure;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricCounterAspect {

    private final MeterRegistry meterRegistry;

    public MetricCounterAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("@annotation(metricCounter)")
    public Object handleMetric(ProceedingJoinPoint pjp, MetricCounter metricCounter) throws Throwable {
        String base = metricCounter.value();

        // Sempre conta o total
        meterRegistry.counter(base + ".total").increment();

        Timer.Sample sample = metricCounter.recordTime() ? Timer.start(meterRegistry) : null;

        try {
            Object result = pjp.proceed();

            if (metricCounter.countSuccess()) {
                meterRegistry.counter(base + ".success").increment();
            }

            return result;
        } catch (Exception e) {
            if (metricCounter.countError()) {
                meterRegistry.counter(base + ".error", "exception", e.getClass().getSimpleName()).increment();
            }
            throw e;
        } finally {
            if (metricCounter.recordTime() && sample != null) {
                sample.stop(Timer.builder(base + ".duration")
                        .publishPercentileHistogram()
                        .description("Duração da operação: " + base)
                        .register(meterRegistry));
            }
        }
    }
}
