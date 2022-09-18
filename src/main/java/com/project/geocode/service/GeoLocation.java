package com.project.geocode.service;

import java.util.List;

import com.project.geocode.dto.LocationDTO;

public interface GeoLocation {

	List<LocationDTO> generateGeoCodes(List<String> cities);

}
