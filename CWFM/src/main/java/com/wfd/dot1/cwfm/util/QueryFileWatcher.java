package com.wfd.dot1.cwfm.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

public class QueryFileWatcher {
    private static final String QUERY_FILE_PATH = "D:/wfd_cwfm/config/queries.properties";
    private static Properties queries = new Properties();

    static {
        loadQueries();
        watchFileChanges();
    }

    private static void loadQueries() {
        try {
            queries.load(Files.newInputStream(Paths.get(QUERY_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load queries from file", e);
        }
    }

    private static void watchFileChanges() {
        Thread thread = new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(QUERY_FILE_PATH).getParent();
                path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY &&
                            event.context().toString().equals(Paths.get(QUERY_FILE_PATH).getFileName().toString())) {
                            loadQueries(); // Reload the queries
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Failed to watch query file", e);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static String getQuery(String key) {
        return queries.getProperty(key);
    }
}

