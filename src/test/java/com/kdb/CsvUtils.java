package com.kdb;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvUtils {

    public static List<Map.Entry<String, String>> readCsv(String filePath) throws IOException, CsvException {
        List<Map.Entry<String, String>> inputList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                if (line.length == 2) {
                    inputList.add(Map.entry(line[0], line[1]));
                }
            }
        }
        return inputList;
    }
}
