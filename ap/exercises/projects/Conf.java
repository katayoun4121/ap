package ap.exercises.ex6;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
public class ConfigReader {
    public static Properties readConfig(String configPath) throws IOException {
        Properties props = new Properties();
        if (Files.exists(Paths.get(configPath))) {
            props.load(Files.newInputStream(Paths.get(configPath)));
        }
        return props;
    }
}
