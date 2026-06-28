package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.apiResponse.WeatherResponse;
import in.kevinlobo.journalApp.cache.AppCache;
import in.kevinlobo.journalApp.constants.PlaceHolders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

//    @Value("${weather.api.key}") When the parameter of any variable is provided in YML file, it will be injected here using the @Value Annotaion
//    private String apiKey;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_"+city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.Keys.WEATHER_API.toString())
                    .replace(PlaceHolders.CITY, city)
                    .replace(PlaceHolders.API_KEY, appCache.appCache.get(AppCache.Keys.API_KEY.toString()));
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_"+city, body, 300L);
            }
            return body;
        }
    }
}
