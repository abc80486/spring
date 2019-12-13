package com.neo;

import java.sql.SQLException;

import javax.activation.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;

@SpringBootApplication
@MapperScan("com.neo.mapper")
public class MybatisAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisAnnotationApplication.class, args);
	}
}
