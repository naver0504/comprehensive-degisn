package com.example.comprehensivedegisn.controller.integration.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ActiveProfiles("test")
@Transactional
public @interface IntegrationTestForController {
}
