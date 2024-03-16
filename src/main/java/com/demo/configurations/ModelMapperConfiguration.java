package com.demo.configurations;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.demo.dtos.CinemaDTO;
import com.demo.dtos.CityDTO;
import com.demo.dtos.HallDTO;
import com.demo.entities.Cinema;
import com.demo.entities.City;
import com.demo.entities.Hall;

@Configuration
public class ModelMapperConfiguration {
	@Autowired
	private Environment environment;

	@Bean
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<City, CityDTO>() {

			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setName(source.getName());

			}

		});

		modelMapper.addMappings(new PropertyMap<Cinema, CinemaDTO>() {

			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setName(source.getName());

			}

		});

		modelMapper.addMappings(new PropertyMap<Hall, HallDTO>() {

			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setName(source.getName());

			}

		});

		return modelMapper;
	}
}
