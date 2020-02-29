package com.lei.unitconversion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lei.unitconversion.mapper")
public class UnitconversionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitconversionApplication.class, args);
    }

}
