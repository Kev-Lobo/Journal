package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.apiResponse.WetherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String api_key = "Copy from your OneNote";

    private static final String api = "http://api.openweathermap.org/data/2.5/weather?q=City,IN&APPID=API_KEY";

    @Autowired
    public RestTemplate restTemplate;

    public WetherResponse getWeather(String city) {

        String finalApi = api.replace("City", city).replace("API_KEY", api_key);
        ResponseEntity<WetherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WetherResponse.class);
        WetherResponse body = response.getBody();
        return body;
    }
}
