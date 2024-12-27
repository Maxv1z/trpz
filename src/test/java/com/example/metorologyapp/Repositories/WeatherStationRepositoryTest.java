package com.example.metorologyapp.Repositories;

import com.example.metorologyapp.Entities.WeatherStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherStationRepositoryTest {

    @MockBean
    private WeatherStationRepository repository;

    private WeatherStation station1;
    private WeatherStation station2;

    @BeforeEach
    void setUp() {
        station1 = new WeatherStation("Station1", "Kyiv", 15.5, 60.0);
        station1.setId(1L);

        station2 = new WeatherStation("Station2", "Lviv", 18.3, 55.0);
        station2.setId(2L);
    }

    @Test
    void testFindAll() {
        // Імітація результату репозиторія
        when(repository.findAll()).thenReturn(Arrays.asList(station1, station2));

        // Виклик тестованого методу
        List<WeatherStation> stations = repository.findAll();

        // Перевірки
        assertNotNull(stations);
        assertEquals(2, stations.size());
        assertEquals("Station1", stations.get(0).getName());

        // Перевірка чи метод викликався
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Імітація результату репозиторія
        when(repository.findById(1L)).thenReturn(Optional.of(station1));

        // Виклик тестованого методу
        Optional<WeatherStation> result = repository.findById(1L);

        // Перевірки
        assertTrue(result.isPresent());
        assertEquals("Station1", result.get().getName());

        // Перевірка чи метод викликався
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        // Імітація результату збереження
        when(repository.save(station1)).thenReturn(station1);

        // Виклик тестованого методу
        WeatherStation savedStation = repository.save(station1);

        // Перевірки
        assertNotNull(savedStation);
        assertEquals("Station1", savedStation.getName());
        assertEquals(15.5, savedStation.getTemperature());

        // Перевірка чи метод викликався
        verify(repository, times(1)).save(station1);
    }

    @Test
    void testDeleteById() {
        // Виклик тестованого методу
        repository.deleteById(1L);

        // Перевірка чи метод викликався
        verify(repository, times(1)).deleteById(1L);
    }
}
