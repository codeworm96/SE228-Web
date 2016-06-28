package bookstore.utility;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by codeworm on 6/5/16.
 */
public class MongoUtil {
    private static final MongoDatabase db = fetchDB();

    private static MongoDatabase fetchDB() {
        // load config from xml file
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        MongoConfig config = (MongoConfig) ac.getBean("MongoConfig");

        // create client
        MongoClient mongoClient = new MongoClient(config.getHost(), config.getPort());
        return mongoClient.getDatabase(config.getDbname());
    }

    public static MongoDatabase getDB() {
        return db;
    }
}