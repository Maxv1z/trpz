package com.example.metorologyapp.Controllers;


import com.example.metorologyapp.DTO.WeatherStationRequest;
import com.example.metorologyapp.DTO.WeatherStationResponse;
import com.example.metorologyapp.Services.WeatherStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather-stations")
public class WeatherStationController {

    @Autowired
    private WeatherStationService service;

    @GetMapping
    public List<WeatherStationResponse> getAllStations() {
        return service.getAllStations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherStationResponse> getStationById(@PathVariable Long id) {
        return service.getStationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WeatherStationResponse createStation(@RequestBody WeatherStationRequest request) {
        return service.createStation(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeatherStationResponse> updateStation(@PathVariable Long id, @RequestBody WeatherStationRequest request) {
        return service.updateStation(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        service.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}
