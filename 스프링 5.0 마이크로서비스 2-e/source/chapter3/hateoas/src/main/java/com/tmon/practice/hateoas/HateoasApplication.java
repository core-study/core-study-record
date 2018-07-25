package com.tmon.practice.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class HateoasApplication {
	public static void main(String[] args) {
		SpringApplication.run(HateoasApplication.class, args);
	}

	@Bean(name = "asyncThreadPoolTaskExecutor")
	public Executor asyncThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(200);
		taskExecutor.setMaxPoolSize(400);
		taskExecutor.setQueueCapacity(200);
		taskExecutor.setThreadNamePrefix("SongRxTestExecutor-");
		taskExecutor.initialize();
		return taskExecutor;
	}
}
