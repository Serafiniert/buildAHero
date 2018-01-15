package de.build_a_hero.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ninad on 15.01.2018.
 */

public class NamesURL {

    String myURL = "http://www.berlin.de/daten/liste-der-vornamen-2016/mitte.csv";
    HttpGetRequest getRequest = new HttpGetRequest();
    String result;
    private List<String> female;
    private List<String> male;
    private List<String> allNames;

    public void read() throws ExecutionException {
        String separator = ";";
        String caseFemale = "w";
        String caseMale = "m";

        female = new ArrayList<>();
        male = new ArrayList<>();
        allNames = new ArrayList<>();

        StringReader reader;
        BufferedReader br;

        try {

            result = getRequest.execute(myURL).get();

            reader = new StringReader(result);
            br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(separator);
                if (row[2].equalsIgnoreCase(caseFemale)) {
                    female.add(row[0]);
                } else if (row[2].equalsIgnoreCase(caseMale)) {
                    male.add(row[0]);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(female);
        Collections.sort(male);
        allNames.addAll(female);
        allNames.addAll(male);
        Collections.sort(allNames);
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
