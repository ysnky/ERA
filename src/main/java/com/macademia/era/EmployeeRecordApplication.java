package com.macademia.era;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = { "com.macademia" })
@EnableJpaRepositories(basePackages = { "com.macademia" })
@EntityScan(basePackages = { "com.macademia" })
public class EmployeeRecordApplication 
{
	protected static final Logger logger = LoggerFactory.getLogger(EmployeeRecordApplication.class);
    public static void main( String[] args ) {
    		new SpringApplicationBuilder().sources(EmployeeRecordApplication.class).run(args);
    }
    
    @PostConstruct
    private void afterInit() {
    		logger.info("--------------------------------------- Employee Record Application ---------------------------------------");    	
    }
}
