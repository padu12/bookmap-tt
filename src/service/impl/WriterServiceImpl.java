package service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import service.WriterService;

public class WriterServiceImpl implements WriterService {
    public void write(String string, String filename) {
        File outputFile = new File(filename);
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        try {
            Files.write(outputFile.toPath(), string.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write date to file", e);
        }
    }
}
