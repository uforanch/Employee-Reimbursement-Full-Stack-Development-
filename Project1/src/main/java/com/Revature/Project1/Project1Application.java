package com.Revature.Project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EntityScan("com.Revature.Models")
@ComponentScan("com.Revature")
@EnableJpaRepositories("com.Revature.DAOs")
@CrossOrigin(origins="http://localhost:9000")
public class Project1Application {

	public static void main(String[] args) {

		SpringApplication.run
				(Project1Application.class, args);
		System.out.println("User and Reimbursement Management System Up and Running");
	}

}
