package com.imooc;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;


// 扫描mybatis mapper包路径
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
//@MapperScan(basePackages="com.imooc.mapper")
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"org.n3r.idworker","com.imooc"})
public class Application {
	
	@Bean
	public SpringUtil getSpingUtil() {
		return new SpringUtil();
	}
	
	public static void main(String[] args) {
		try{
		SpringApplication.run(Application.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
