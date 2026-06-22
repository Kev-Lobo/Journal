package in.kevinlobo.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
    /*
    * This is the transaction manager bean that will be used to manage transactions with the database.
    * PlatformTransactionManager is the interface that all transaction managers must implement.
    * MongoTransactionManager is the implementation of PlatformTransactionManager that uses MongoDB transactions.
    * dbFactory is the factory that creates the MongoDB database connection.
    */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}