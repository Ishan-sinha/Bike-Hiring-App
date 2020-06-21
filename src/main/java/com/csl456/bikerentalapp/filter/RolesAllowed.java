package com.csl456.bikerentalapp.filter;

import com.csl456.bikerentalapp.core.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RolesAllowed {

    UserRole[] value();

}
