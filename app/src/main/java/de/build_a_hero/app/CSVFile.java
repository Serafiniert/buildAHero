package de.build_a_hero.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVFile {
    private InputStream inputStream;
    private List<String> female;
    private List<String> male;
    private List<String> allNames;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        female = new ArrayList<>();
        male = new ArrayList<>();
        allNames = new ArrayList<>();

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                if (row[2].equalsIgnoreCase("w")) {
                    female.add(row[0]);
                } else if (row[2].equalsIgnoreCase("m")) {
                    male.add(row[0]);
                }

            }
            allNames.addAll(female);
            allNames.addAll(male);
            Collections.sort(allNames);
            Collections.sort(female);
            Collections.sort(male);

        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }


    public List<String> getFemale() {
        return female;
    }

    public void setFemale(List<String> female) {
        this.female = female;
    }

    public List<String> getMale() {
        return male;
    }

    public void setMale(List<String> male) {
        this.male = male;
    }

    public List<String> getAllNames() {
        return allNames;
    }

    public void setAllNames(List<String> allNames) {
        this.allNames = allNames;
    }
}
