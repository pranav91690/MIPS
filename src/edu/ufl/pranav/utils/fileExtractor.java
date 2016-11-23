package edu.ufl.pranav.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pranav on 9/23/16.
 */
public class fileExtractor {
    String inputfileName;

    public fileExtractor(String inputfileName) {
        this.inputfileName = inputfileName;
    }

    public byte[] extractInstructions() throws IOException{
        Path path = Paths.get(inputfileName);
        return Files.readAllBytes(path);
    }
}
