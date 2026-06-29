package in.kevinlobo.journalApp.scheduler;

import in.kevinlobo.journalApp.cache.AppCache;
import in.kevinlobo.journalApp.entity.JournalEntry;
import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.enums.Sentiment;
import in.kevinlobo.journalApp.model.SentimentData;
import in.kevinlobo.journalApp.repository.UserRepositoryImpl;
import in.kevinlobo.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AppCache appCache1;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

//    @Scheduled(cron ="0 0 9 * * SUN ")
    public void fetchUsersAndSendEmails() {
        List<User> usersForSA = userRepository.getUsersForSA();
        for (User user : usersForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiments for last 7 days " + mostFrequentSentiment.toString()).build();
                kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
            }
        }
    }

    @Scheduled(cron ="0 0/10 * 1/1 * ?")
    public void clearAppCache(){
        appCache1.initCache();
    }
}
