package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.apiResponse.WetherResponse;
import in.kevinlobo.journalApp.cache.AppCache;
import in.kevinlobo.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

//    @Value("${weather.api.key}") When the parameter of any variable is provided in YML file, it will be injected here using the @Value Annotaion
//    private String apiKey;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public AppCache appCache;

    @Autowired
    public RedisService redisService;

    public WetherResponse getWeather(String city) {
        WetherResponse wetherResponse = redisService.get("weather_of_"+city, WetherResponse.class);
        if(wetherResponse != null){
            return wetherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                    .replace(PlaceHolders.CITY, city)
                    .replace(PlaceHolders.API_KEY, appCache.appCache.get(AppCache.keys.API_KEY.toString()));
            ResponseEntity<WetherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WetherResponse.class);
            WetherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_"+city, body, 300L);
            }
            return body;
        }
    }
}
