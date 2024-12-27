package com.example.metorologyapp.Repositories;

import com.example.metorologyapp.Entities.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {
}
