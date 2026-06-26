package in.kevinlobo.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dbconfig")
@Data
@NoArgsConstructor
public class DbConfigEntity {
    private String id;
    private String key;
    private String host;
    private int port;
    private String userName;
    private String password;
}
