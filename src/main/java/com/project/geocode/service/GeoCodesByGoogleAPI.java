package com.project.geocode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.project.geocode.dto.LocationDTO;

public class GeoCodesByGoogleAPI implements GeoLocation {

	static Logger LOGGER = LoggerFactory.getLogger(GeoCodesByGoogleAPI.class);

	@Value("${api.key}")
	String apiKey;

	@Override
	public List<LocationDTO> generateGeoCodes(List<String> cities) {
		List<LocationDTO> latLongPairs = new ArrayList<>();

		try {
			GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
			for (String city : cities) {
				GeocodingResult[] results = GeocodingApi.geocode(context, city).await();
				Gson gson = new GsonBuilder().create();
				if (Objects.nonNull(results)) {
					Double latitude = Double.valueOf(gson.toJson(results[0].geometry.location.lat));
					Double longitude = Double.valueOf(gson.toJson(results[0].geometry.location.lat));
					latLongPairs.add(new LocationDTO(latitude, longitude));

				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in -> GeoCodesByGoogleAPI", ex);
		}
		return latLongPairs;
	}

}
