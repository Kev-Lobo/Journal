package in.kevinlobo.journalApp.cache;

import in.kevinlobo.journalApp.entity.ConfigJournalAppEntity;
import in.kevinlobo.journalApp.repository.ConfigJournalApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigJournalApp configJournalApp;

    public Map<String, String> appCache;


    @PostConstruct
    public void initCache() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all =configJournalApp.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity: all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
