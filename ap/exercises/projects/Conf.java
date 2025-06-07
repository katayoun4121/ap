package ap.exercises.projects;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class Conf {
    private static final String DEFAULT_CONFIG_PATH = "config.properties";
    private final Properties properties;
    private final Path configPath;

    private static final String DEFAULT_TARGET_DOMAIN = "znu.ac.ir";
    private static final String DEFAULT_DOWNLOAD_DIR = "downloaded_media";
    private static final int DEFAULT_DOWNLOAD_DELAY = 5;
    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final int DEFAULT_RETRY_DELAY = 10;
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private static final int DEFAULT_TIMEOUT = 30;
    private static final int DEFAULT_PARALLEL_DOWNLOADS = 1;
    private static final String DEFAULT_LOG_LEVEL = "INFO";

    public Conf() throws IOException {
        this(DEFAULT_CONFIG_PATH);
    }

    public Conf(String configFilePath) throws IOException {
        this.configPath = Paths.get(configFilePath);
        this.properties = new Properties();
        loadConf();
    }

    private void loadConf() throws IOException {
        Stream.of(
                new String[]{"target_domain", DEFAULT_TARGET_DOMAIN},
                new String[]{"download_base_dir", DEFAULT_DOWNLOAD_DIR},
                new String[]{"download_delay_seconds", String.valueOf(DEFAULT_DOWNLOAD_DELAY)},
                new String[]{"max_retries", String.valueOf(DEFAULT_MAX_RETRIES)},
                new String[]{"retry_delay_seconds", String.valueOf(DEFAULT_RETRY_DELAY)},
                new String[]{"user_agent", DEFAULT_USER_AGENT},
                new String[]{"timeout_seconds", String.valueOf(DEFAULT_TIMEOUT)},
                new String[]{"parallel_downloads", String.valueOf(DEFAULT_PARALLEL_DOWNLOADS)},
                new String[]{"log_level", DEFAULT_LOG_LEVEL}
        ).forEach(pair -> properties.setProperty(pair[0], pair[1]));

        if (Files.exists(configPath)) {
            try (InputStream input = Files.newInputStream(configPath)) {
                Properties fileProps = new Properties();
                fileProps.load(input);

                fileProps.forEach((key, value) ->
                        properties.setProperty(key.toString(), value.toString()));
            }
        } else {
            saveConf();
        }
    }

    public void saveConf() throws IOException {
        Files.createDirectories(configPath.getParent());
        try (OutputStream output = Files.newOutputStream(configPath)) {
            properties.store(output, "Media Downloader Configuration");
        }
    }


    public String getTargetDomain() {
        return properties.getProperty("target_domain");
    }

    public Path getDownloadBaseDir() {
        return Paths.get(properties.getProperty("download_base_dir"));
    }

    public int getDownloadDelaySeconds() {
        return getIntProperty("download_delay_seconds", DEFAULT_DOWNLOAD_DELAY);
    }

    public int getMaxRetries() {
        return getIntProperty("max_retries", DEFAULT_MAX_RETRIES);
    }

    public int getRetryDelaySeconds() {
        return getIntProperty("retry_delay_seconds", DEFAULT_RETRY_DELAY);
    }

    public String getUserAgent() {
        return properties.getProperty("user_agent");
    }

    public int getTimeoutSeconds() {
        return getIntProperty("timeout_seconds", DEFAULT_TIMEOUT);
    }

    public int getParallelDownloads() {
        return getIntProperty("parallel_downloads", DEFAULT_PARALLEL_DOWNLOADS);
    }

    public String getLogLevel() {
        return properties.getProperty("log_level");
    }



    public Conf setTargetDomain(String domain) {
        properties.setProperty("target_domain", domain);
        return this;
    }

    public Conf setDownloadBaseDir(String dir) {
        properties.setProperty("download_base_dir", dir);
        return this;
    }

    public Conf setDownloadDelaySeconds(int seconds) {
        properties.setProperty("download_delay_seconds", String.valueOf(seconds));
        return this;
    }

    public Conf setMaxRetries(int retries) {
        properties.setProperty("max_retries", String.valueOf(retries));
        return this;
    }

    public Conf setRetryDelaySeconds(int seconds) {
        properties.setProperty("retry_delay_seconds", String.valueOf(seconds));
        return this;
    }

    public Conf setTimeoutSeconds(int seconds) {
        properties.setProperty("timeout_seconds", String.valueOf(seconds));
        return this;
    }

    public Conf setParallelDownloads(int count) {
        properties.setProperty("parallel_downloads", String.valueOf(count));
        return this;
    }

    public Conf setLogLevel(String level) {
        properties.setProperty("log_level", level);
        return this;
    }



    private int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void printConfigSummary() {
        System.out.println("=== Configuration Summary ===");
        System.out.printf("Target Domain: %s%n", getTargetDomain());
        System.out.printf("Download Directory: %s%n", getDownloadBaseDir());
        System.out.printf("Download Delay: %d seconds%n", getDownloadDelaySeconds());
        System.out.printf("Max Retries: %d%n", getMaxRetries());
        System.out.printf("Retry Delay: %d seconds%n", getRetryDelaySeconds());
        System.out.printf("Timeout: %d seconds%n", getTimeoutSeconds());
        System.out.printf("Parallel Downloads: %d%n", getParallelDownloads());
        System.out.printf("Log Level: %s%n", getLogLevel());
    }

    public Properties getProperties() {
        return new Properties(properties);
    }
}