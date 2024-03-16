package com.demo.controllers.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dtos.HallDTO;
import com.demo.entities.Hall;
import com.demo.services.CinemaService;
import com.demo.services.HallService;

@RestController
@RequestMapping({ "ajax/hall" })
public class HallAjaxController {

	@Autowired
	public CinemaService cinemaService;
	@Autowired
	public HallService hallService;
	@Autowired
	private ModelMapper modelMapper;

//	@GetMapping("/gethallbycinemaid")
//	public List<Hall> getHallsByCinema(@RequestParam("cinemaId") Integer cinemaId) {
//		return hallService.findHallsByCinemaId(cinemaId);
//	}

	@GetMapping("/halls")
	public ResponseEntity<List<HallDTO>> getHallsByCinema(@RequestParam("cinemaId") Integer cinemaId) {
		List<Hall> halls = hallService.findHallsByCinemaId(cinemaId);

		// Configure ModelMapper for custom mappings, if needed


		// Convert Hall entities to HallDTO objects using ModelMapper
		List<HallDTO> hallDTOs = halls.stream().map(hall -> modelMapper.map(hall, HallDTO.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(hallDTOs);
	}

}
