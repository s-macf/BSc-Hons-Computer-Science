package com.scotland_yard.classes.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static FileReader fileReader = new FileReader();

    public static ArrayList<String> readData(String path) {
        try {
            return fileReader.readDataFromFile(path);
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> readDataFromFile(String path) throws FileNotFoundException, URISyntaxException {
        ArrayList<String> data = new ArrayList<>();

        File myFile = new File(Paths.get(getClass().getResource(path).toURI()).toString());
        Scanner fileReader = new Scanner(myFile);
        while (fileReader.hasNextLine()) {
            data.add(fileReader.nextLine());
        }
        fileReader.close();

        return data;
    }

}
