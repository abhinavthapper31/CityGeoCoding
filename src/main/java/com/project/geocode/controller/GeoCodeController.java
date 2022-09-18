package com.project.geocode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.geocode.service.GeoCodeService;

@RestController
@RequestMapping("api/v1")
public class GeoCodeController {

	@Autowired
	GeoCodeService geoCodeService;

	@PostMapping("/generate-geo-code")
	ResponseEntity<Object> genrateGeoCodes() {
	    return geoCodeService.getGeocodeData();
	  }

}
