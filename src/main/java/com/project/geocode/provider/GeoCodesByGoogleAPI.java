package com.project.geocode.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.project.geocode.cache.CustomInMemoryCache;
import com.project.geocode.dto.LocationDTO;

@Component
@PropertySource("classpath:application.properties")
public class GeoCodesByGoogleAPI implements GeoLocation {

	static Logger LOGGER = LoggerFactory.getLogger(GeoCodesByGoogleAPI.class);
	private final String API_KEY = "";

	@Override
	public List<LocationDTO> generateGeoCodes(List<String> cities) {
		List<LocationDTO> latLongPairs = new ArrayList<>();
		CustomInMemoryCache cache = CustomInMemoryCache.getInMemoryCache();
		try {
			GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
			for (String city : cities) {
				if (cache.haskey(city)) {
					LocationDTO cacheValue = (LocationDTO) cache.getDataFromCache(city);
					latLongPairs.add(cacheValue);
				} else {
					GeocodingResult[] results = GeocodingApi.geocode(context, city).await();
					Gson gson = new GsonBuilder().create();
					if (Objects.nonNull(results)) {
						Double latitude = Double.valueOf(gson.toJson(results[0].geometry.location.lat));
						Double longitude = Double.valueOf(gson.toJson(results[0].geometry.location.lat));
						cache.setDataInCache(city, new LocationDTO(latitude, longitude, city));
						latLongPairs.add(new LocationDTO(latitude, longitude, city));

					}
				}

			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in -> GeoCodesByGoogleAPI", ex);
		}
		return latLongPairs;
	}

}
