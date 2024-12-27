package com.example.metorologyapp.Services;

import com.example.metorologyapp.DTO.WeatherStationRequest;
import com.example.metorologyapp.DTO.WeatherStationResponse;
import com.example.metorologyapp.Entities.WeatherStation;
import com.example.metorologyapp.Repositories.WeatherStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherStationService {

    @Autowired
    private WeatherStationRepository repository;

    public List<WeatherStationResponse> getAllStations() {
        return repository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<WeatherStationResponse> getStationById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponse);
    }

    public WeatherStationResponse createStation(WeatherStationRequest request) {
        WeatherStation station = convertToEntity(request);
        WeatherStation savedStation = repository.save(station);
        return convertToResponse(savedStation);
    }

    public Optional<WeatherStationResponse> updateStation(Long id, WeatherStationRequest request) {
        return repository.findById(id).map(existingStation -> {
            existingStation.setName(request.getName());
            existingStation.setLocation(request.getLocation());
            existingStation.setTemperature(request.getTemperature());
            existingStation.setHumidity(request.getHumidity());
            return convertToResponse(repository.save(existingStation));
        });
    }

    public void deleteStation(Long id) {
        repository.deleteById(id);
    }

    // Конвертація з Entity в Response DTO
    private WeatherStationResponse convertToResponse(WeatherStation station) {
        return new WeatherStationResponse(
                station.getId(),
                station.getName(),
                station.getLocation(),
                station.getTemperature(),
                station.getHumidity()
        );
    }

    // Конвертація з Request DTO в Entity
    private WeatherStation convertToEntity(WeatherStationRequest request) {
        return new WeatherStation(
                request.getName(),
                request.getLocation(),
                request.getTemperature(),
                request.getHumidity()
        );
    }
}
