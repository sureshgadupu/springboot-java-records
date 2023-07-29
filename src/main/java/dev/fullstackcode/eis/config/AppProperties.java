package dev.fullstackcode.eis.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix="app")
public record AppProperties (@NotNull String propertyOne,@Positive int propertyTwo) {
}



//@ConfigurationProperties(prefix="app")
//public record AppProperties (String propertyOne,int propertyTwo) {
//    public AppProperties{
//        if(!StringUtils.hasLength(propertyOne) || propertyTwo <= 0) {
//            throw new IllegalArgumentException("Data is invalid");
//        }
//    }
//}
