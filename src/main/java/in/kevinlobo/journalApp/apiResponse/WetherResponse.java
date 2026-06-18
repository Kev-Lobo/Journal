package in.kevinlobo.journalApp.apiResponse;
//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WetherResponse {
    public List<Weather> weather;
    public Main main;
    public String name;

    @Getter
    @Setter
    public static class Main{
        public double temp;
        @JsonProperty("feels_like")
        public int feelsLike;

    }

    @Getter
    @Setter
    public static class Weather{
        public int id;
        public String main;
        public String description;

    }

}
