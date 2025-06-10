package ap.exercises.EX7;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "config.properties";

    public static DataStorage createStorage() {
        Properties prop = new Properties();
        try {
            File configFile = new File(CONFIG_FILE);
            if (!configFile.exists()) {
                prop.setProperty("storage", "tabsplit");
                prop.store(new java.io.FileOutputStream(CONFIG_FILE), "Library System Configuration");
            } else {
                prop.load(new FileReader(CONFIG_FILE));
            }

            String storageType = prop.getProperty("storage", "tabsplit").toLowerCase();
            switch (storageType) {
                case "json":
                   return new JsonStorage();
                case "sqlite":
                    return new SqliteStorage();
                case "tabsplit":
                default:
                    return new TabSplitStorage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new TabSplitStorage();
        }
    }
}
