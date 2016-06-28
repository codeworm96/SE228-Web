package bookstore.utility;

/**
 * Created by codeworm on 6/5/16.
 *
 * Object to represent a mongodb config
 */
public class MongoConfig {
    private String host;
    private int port;
    private String dbname;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
}
