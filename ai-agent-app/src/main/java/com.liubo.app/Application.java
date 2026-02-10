package com.liubo.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Configurable
@MapperScan(basePackages = "com.liubo.infrastructure.dao")
@ComponentScan(basePackages = "com.liubo")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

}
