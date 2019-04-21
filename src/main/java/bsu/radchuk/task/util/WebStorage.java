package bsu.radchuk.task.util;


import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebStorage {
    @SneakyThrows(UnsupportedEncodingException.class)
    public static void uploadFile(InputStream file,
                                  String relativePath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String encodedClassPath = classLoader.getResource("").getPath();
        String classPath = URLDecoder.decode(encodedClassPath, "UTF-8");
        if (classPath.startsWith("/")) {
            classPath = classPath.substring(1);
        }
        String webStoragePath = classPath
                .substring(0, classPath.length() - "WEB-INF/classes".length());
        Files.copy(file, Paths.get(webStoragePath));
    }
}
