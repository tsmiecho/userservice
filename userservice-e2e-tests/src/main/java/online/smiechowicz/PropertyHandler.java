package online.smiechowicz;

import java.io.*;
import java.util.Properties;

import static java.util.Optional.ofNullable;

public class PropertyHandler {

    private static final Properties props = new Properties();

    static {
        try (InputStream fis = ofNullable(System.getProperty("config"))
                .map(s -> {
                    try {
                        return (InputStream)new FileInputStream(s);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException("Missing file with tests configuration");
                    }
                })
                .orElseGet(() -> PropertyHandler.class
                        .getClassLoader()
                        .getResourceAsStream("local.properties"))) {
            props.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Missing file with tests configuration");
        }
    }

    public static final String BACKEND_HOST = props.getProperty("userservice.backend.host");
}
