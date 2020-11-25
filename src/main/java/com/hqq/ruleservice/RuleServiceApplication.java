package com.hqq.ruleservice;

import com.hqq.ruleservice.drools.loader.RuleLoader;
import com.hqq.ruleservice.model.domain.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RuleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuleServiceApplication.class,args);
	}
}
