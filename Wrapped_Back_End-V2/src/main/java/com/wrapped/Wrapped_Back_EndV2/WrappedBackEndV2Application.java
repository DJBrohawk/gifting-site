package com.wrapped.Wrapped_Back_EndV2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wrapped")
public class WrappedBackEndV2Application  {

	public static void main(String[] args) {SpringApplication.run(WrappedBackEndV2Application.class, args);}


}
