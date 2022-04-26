package api.helpers;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {

    @SneakyThrows
    public static String generateStringFromResource(String path) {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
