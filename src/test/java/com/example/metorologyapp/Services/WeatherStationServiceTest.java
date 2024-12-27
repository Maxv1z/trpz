package com.example.metorologyapp.Services;

import com.example.metorologyapp.DTO.WeatherStationRequest;
import com.example.metorologyapp.DTO.WeatherStationResponse;
import com.example.metorologyapp.Entities.WeatherStation;
import com.example.metorologyapp.Repositories.WeatherStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherStationServiceTest {

    @Mock
    private WeatherStationRepository repository;

    @InjectMocks
    private WeatherStationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStations_ShouldReturnAllStations() {
        // Arrange
        List<WeatherStation> mockStations = Arrays.asList(
                new WeatherStation("Station1", "Kyiv", 20.5, 60.0),
                new WeatherStation("Station2", "Lviv", 15.0, 70.0)
        );

        when(repository.findAll()).thenReturn(mockStations);

        // Act
        List<WeatherStationResponse> result = service.getAllStations();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Station1", result.get(0).getName());
        assertEquals("Station2", result.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getStationById_ShouldReturnStation_WhenStationExists() {
        // Arrange
        WeatherStation mockStation = new WeatherStation("Station1", "Kyiv", 20.5, 60.0);
        mockStation.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(mockStation));

        // Act
        Optional<WeatherStationResponse> result = service.getStationById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Station1", result.get().getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getStationById_ShouldReturnEmpty_WhenStationDoesNotExist() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<WeatherStationResponse> result = service.getStationById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void createStation_ShouldSaveAndReturnStation() {
        // Arrange
        WeatherStationRequest request = new WeatherStationRequest("Station1", "Kyiv", 20.5, 60.0);
        WeatherStation mockStation = new WeatherStation("Station1", "Kyiv", 20.5, 60.0);
        mockStation.setId(1L);
        when(repository.save(any(WeatherStation.class))).thenReturn(mockStation);

        // Act
        WeatherStationResponse result = service.createStation(request);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Station1", result.getName());
        verify(repository, times(1)).save(any(WeatherStation.class));
    }

    @Test
    void updateStation_ShouldUpdateAndReturnStation_WhenStationExists() {
        // Arrange
        WeatherStation existingStation = new WeatherStation("Station1", "Kyiv", 20.5, 60.0);
        existingStation.setId(1L);

        WeatherStationRequest updateRequest = new WeatherStationRequest("UpdatedStation", "Lviv", 25.0, 65.0);

        WeatherStation updatedStation = new WeatherStation("UpdatedStation", "Lviv", 25.0, 65.0);
        updatedStation.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingStation));
        when(repository.save(any(WeatherStation.class))).thenReturn(updatedStation);

        // Act
        Optional<WeatherStationResponse> result = service.updateStation(1L, updateRequest);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("UpdatedStation", result.get().getName());
        assertEquals("Lviv", result.get().getLocation());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(WeatherStation.class));
    }

    @Test
    void updateStation_ShouldReturnEmpty_WhenStationDoesNotExist() {
        // Arrange
        WeatherStationRequest updateRequest = new WeatherStationRequest("UpdatedStation", "Lviv", 25.0, 65.0);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<WeatherStationResponse> result = service.updateStation(1L, updateRequest);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(WeatherStation.class));
    }

    @Test
    void deleteStation_ShouldCallDeleteById() {
        // Arrange
        Long stationId = 1L;

        // Act
        service.deleteStation(stationId);

        // Assert
        verify(repository, times(1)).deleteById(stationId);
    }
}
