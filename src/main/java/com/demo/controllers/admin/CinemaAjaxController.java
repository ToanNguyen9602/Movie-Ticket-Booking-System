package com.demo.controllers.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dtos.CinemaDTO;
import com.demo.dtos.HallDTO;
import com.demo.entities.Cinema;
import com.demo.entities.Hall;
import com.demo.services.CinemaService;
import com.demo.services.CityService;
import com.demo.services.HallService;

@RestController
@RequestMapping({ "ajax" })
public class CinemaAjaxController {

	@Autowired
	public CinemaService cinemaService;
	@Autowired
	public CityService cityService;
	@Autowired
	public HallService hallService;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = { "cinemas" }, method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<CinemaDTO> findCinemasByCityId(@RequestParam("cityid") int cityid) {
		return cinemaService.findallCinemabyCityid(cityid);
	}

	@RequestMapping(value = { "halls" }, method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<HallDTO> findHallsByCinemaId(@RequestParam("cinemaid") int cinemaid) {
		return hallService.findHallDTObyCinemaID(cinemaid);
	}

}
