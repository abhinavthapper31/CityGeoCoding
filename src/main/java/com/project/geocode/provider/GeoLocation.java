package com.project.geocode.provider;

import java.util.List;

import com.project.geocode.dto.LocationDTO;

public interface GeoLocation {

	List<LocationDTO> generateGeoCodes(List<String> cities);

}
