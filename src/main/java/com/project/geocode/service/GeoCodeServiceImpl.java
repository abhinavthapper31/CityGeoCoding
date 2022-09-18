package com.project.geocode.service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.project.geocode.dto.LocationDTO;
import com.project.geocode.exception.InputStreamException;
import com.project.geocode.exception.NoCityFoundException;

@Service
public class GeoCodeServiceImpl implements GeoCodeService {
	static Logger LOGGER = LoggerFactory.getLogger(GeoCodeServiceImpl.class);

	@Value("${api.key}")
	String apiKey;

	@Override
	public ResponseEntity<Object> getGeocodeData() {
		try {
			List<String> cities = getCityList();
			if (cities.isEmpty()) {
				LOGGER.info("No cities found");
				throw new NoCityFoundException("No cities found in the input file");
			}
			List<LocationDTO> latLongPairs = generateLatitudeLongitudePairList(cities);
			writeOutputToFile(latLongPairs);
			return new ResponseEntity<>("Generation Success", HttpStatus.OK);

		} catch (Exception ex) {
			LOGGER.error("Exception occured in -> getGeocodeData", ex);
		}
		return null;
	}

	private List<LocationDTO> generateLatitudeLongitudePairList(List<String> cities)
			throws ApiException, InterruptedException, IOException {
		GeoCodesByGoogleAPI geoCodesByGoogleAPI = new GeoCodesByGoogleAPI();
		return geoCodesByGoogleAPI.generateGeoCodes(cities);

	}

	private void writeOutputToFile(List<LocationDTO> latLongPairs) throws IOException {
		FileWriter writer = new FileWriter("output.txt");
		for (LocationDTO location : latLongPairs) {
			writer.write(location.getLatitude().toString() + "," + location.getLongitude().toString());
		}
		writer.close();
	}

	private List<String> getCityList() throws IOException, InputStreamException {
		List<String> cities = new ArrayList<>();
		InputStream inputStream = getFileStreamFromResourceFolder();
		if (Objects.isNull(inputStream)) {
			throw new InputStreamException("Error while generating input stream");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = br.readLine()) != null) {
			cities.add(line);
		}
		return cities;
	}

	private InputStream getFileStreamFromResourceFolder() {
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("input.txt");
			return is;
		} catch (Exception ex) {
			LOGGER.error("Error while reading file", ex);
		}
		return null;
	}

}
