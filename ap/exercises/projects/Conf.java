package ap.exercises.ex6;

import java.util.Properties;
public class Conf {
    public static final String DOMAIN_ADDRESS = "https://znu.ac.ir";
    public static final String SAVE_DIRECTORY = "fetched_html";
    public static final int DOWNLOAD_DELAY_SECONDS = 2;
    public static final int THREAD_COUNT = loadThreadCount();
    private static int loadThreadCount() {
        try {
            Properties props = ConfigReader.readConfig("config.properties");
            String threadCountStr = props.getProperty("thread-count", "0");
            return Integer.parseInt(threadCountStr);
        } catch (Exception e) {
            return 0;
        }
    }
}
