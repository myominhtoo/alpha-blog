package com.lio.BlogApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(new File("src/main/resources/static/mail-templates/mail-verification.txt")));
        try {
            System.out.println(bufferedReader.readLine().replace("[[name]]", "Myo Min Htoo"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bufferedReader.close();
    }
}
