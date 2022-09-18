package com.project.geocode.service;

import org.springframework.http.ResponseEntity;

public interface GeoCodeService {
	public ResponseEntity<Object> getGeocodeData();
}
