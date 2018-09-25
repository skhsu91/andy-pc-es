package com.andy.app;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.*;

/**
 * Converter for the CSV file into JSON for ElasticSearch to consume appropriately
 */
public class JSONConverter {
    /**
     * Main method that will run a conversion of the csv file located in the resources directory into a usable json
     * file for other parts of the application
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            for (int fileCount = 1; fileCount <= 89; fileCount++) {
                FileInputStream inputStream = new FileInputStream(new File("/Users/skhsu91/Workspaces/andy-pc-es/src/main/resources/csv/file-" + fileCount + ".csv"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                convert(bufferedReader, fileCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getHeaders() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/skhsu91/Workspaces/andy-pc-es/src/main/resources/header.csv"));
        return bufferedReader.readLine().split(",");
    }

    private static void convert(BufferedReader bufferedReader, int fileCount) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File("json/result-" + fileCount + ".json"), JsonEncoding.UTF8);
        String entry;
        String[] headers = getHeaders();
        for (int i = 0; (entry = bufferedReader.readLine()) != null; i++) {
            jsonGenerator.writeRaw("{\"index\" : { \"_index\": \"plans\", \"_type\" : \"entry\", \"_id\" : \"" + ((fileCount * 10000) + i) + "\"}}\n");
            jsonGenerator.writeStartObject();
            String[] delimited = entry.split(",");

            for (int j = 0; j < headers.length; j++) {
                if (j < delimited.length)
                    jsonGenerator.writeObjectField(headers[j], delimited[j]);
                else
                    jsonGenerator.writeObjectField(headers[j], null);
            }
            jsonGenerator.writeEndObject();
            jsonGenerator.writeRaw("\n");
        }
        bufferedReader.close();
    }
}