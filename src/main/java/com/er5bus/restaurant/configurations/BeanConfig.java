package com.er5bus.restaurant.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
