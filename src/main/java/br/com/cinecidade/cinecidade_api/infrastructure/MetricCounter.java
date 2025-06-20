package br.com.cinecidade.cinecidade_api.infrastructure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetricCounter {

    /**
     * Nome base da métrica. Ex: "movies.create"
     */
    String value();

    /**
     * Se deve contar sucesso. Default: false
     */
    boolean countSuccess() default false;

    /**
     * Se deve contar erros. Default: false
     */
    boolean countError() default false;

    /**
     * Se deve medir o tempo. Default: false
     */
    boolean recordTime() default false;
}