package com.extraallt.extraallt;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.extraallt.extraallt.models.Temperature;
import com.extraallt.extraallt.services.TemperatureService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TemperatureTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemperatureService temperatureService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddTemperature() throws Exception {
        Temperature temperature = new Temperature();
        temperature.setCelcius(25.0);

        Mockito.when(temperatureService.addTemperature(Mockito.any(Temperature.class))).thenReturn(temperature);

        mockMvc.perform(post("/temperature").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(temperature)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.celcius").value(25.0));
    }
}
