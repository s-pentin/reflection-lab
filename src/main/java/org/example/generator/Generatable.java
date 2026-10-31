package org.example.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  Маркитрующая аннотаця, которая говорит, что выбранная реализацию разрешено генерировть
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Generatable {
    // какой интерфейс/абстрактный класс реализует
    Class<?> implement();
}
