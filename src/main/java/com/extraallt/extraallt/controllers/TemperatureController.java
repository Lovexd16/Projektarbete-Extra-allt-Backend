package com.extraallt.extraallt.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.extraallt.extraallt.models.Temperature;
import com.extraallt.extraallt.services.TemperatureService;

@CrossOrigin(origins = "*")
@RestController
public class TemperatureController {

    private TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping("/temperatures")
    public List<Temperature> getTemperatures() {
        System.out.println("Hämtar temperaturer...");
        return temperatureService.getTemperatures();
    }

    @PostMapping("/temperature")
    public Temperature addTemperature(@RequestBody Temperature temperature) {
        System.out.println("Mottagen temperatur: " + temperature.getCelcius() + " °C");
        return temperatureService.addTemperature(temperature);
    }

}
