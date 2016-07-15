package com.lector.util.excel.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class SteamOperationUtil {

    public static final String UTF_8 = "utf-8";

    public static List<String> readLines(InputStream inputStream){
        final List<String> strings = new ArrayList<>();
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                strings.add(line);
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from stream.");
        }
    }

    public static void writeLines(OutputStream outputStream, List<String> lines){
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        try{
            for (String line : lines) {
                bufferedWriter.write(line);
            }
        } catch (IOException e){
            throw new RuntimeException("Cannot write into stream.");
        }
    }

    public static void writeLine(OutputStream outputStream, String line){
        try{
            outputStream.write(getUtfBytes(line));
            outputStream.write(getUtfBytes("\r\n"));
        } catch (IOException e){
            throw new RuntimeException("Cannot write into stream.");
        }
    }

    private static byte[] getUtfBytes(String line) throws UnsupportedEncodingException {
        return line.getBytes(UTF_8);
    }
}
