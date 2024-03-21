package com.progressive.code.jdp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonStreamUtils {

    private static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * Simple helper method to create a {@link JsonParser} instance used for
     * streaming JSON data from a file.
     * @param file the path to the JSON file to read
     * @return {@link JsonParser} instance
     * @throws JsonParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static JsonParser getJsonParser(String file) throws JsonParseException, FileNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        return JSON_FACTORY.createParser(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
    }

    /**
     * This methods is used to count the number of objects within an array. The attribute name that
     * references the array in the JSON file has to be provided. This method can only be used for
     * scenarios where the array only contains "flat objects" - which means the object itself
     * doesn't include arrays.
     * @param file the path to the JSON file to read
     * @param arrayAttributeName the attribute name that references the array
     * @return the number of objects within the array
     * @throws JsonParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Long countArrayItems(String file, String arrayAttributeName) throws JsonParseException, FileNotFoundException, IOException {
        long counter = 0;
        // Get JsonParser
        JsonParser jsonParser = JsonStreamUtils.getJsonParser(file);

        // Find the start of the array
        if(JsonStreamUtils.findStartOfArray(jsonParser, arrayAttributeName)) {
    
            // The pointer will be a the start of the first object when we call jsonParser.nextToken() 
            // This approach only works for flat objects, that don't have arrays within the object structure. 
            while(jsonParser.nextToken() != null && !jsonParser.currentToken().equals(JsonToken.END_ARRAY)) {
                while(!jsonParser.currentToken().equals(JsonToken.END_OBJECT)) {
                    jsonParser.nextToken();
                }
                // When we reach the end of an object, we can increase the counter
                counter++;
            }
        }

        return counter;
    }

    /**
     * This method is used to count the number of objects within an array based on a boolean value
     * within the objects stored in the array.
     * @param file the path to the JSON file to read
     * @param arrayAttributeName the attribute name that references the array
     * @param filterAttribute the boolean attribute objects are filtered by
     * @param mustBeTrue determines if the value of the filterAttribute has to be true or false
     * @return the number of objects within the array that match the boolean condition
     * @throws JsonParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Long countByBooleanCondition(String file, String arrayAttributeName, String filterAttribute, boolean mustBeTrue) throws JsonParseException, FileNotFoundException, IOException {
        long counter = 0;
        // Get JsonParser
        JsonParser jsonParser = JsonStreamUtils.getJsonParser("./data/customer-data.json");

        // Find the start of the array
        if(JsonStreamUtils.findStartOfArray(jsonParser, arrayAttributeName)) {
    
            // The pointer will be a the start of the first object when we call jsonParser.nextToken() 
            // This approach only works for flat objects, that don't have arrays within the object structure. 
            while(jsonParser.nextToken() != null && !jsonParser.currentToken().equals(JsonToken.END_ARRAY)) {
                while(!jsonParser.currentToken().equals(JsonToken.END_OBJECT)) {
                    if(jsonParser.currentToken().equals(JsonToken.FIELD_NAME) && filterAttribute.equals(jsonParser.getText())) {
                        jsonParser.nextToken();
                        if(jsonParser.currentToken().isBoolean() && jsonParser.getBooleanValue() == mustBeTrue) {
                            counter++;
                        }
                    }
                    jsonParser.nextToken();
                }
            }
        }

        return counter;
    }

    /**
     * This method reads a JSON file, identifies the array referenced by a given arrayAttributeName
     * and generates a new report file only including the specified fieldNames
     * @param sourceFile the path to the JSON file to read
     * @param targetFile the path to the JSON file to write
     * @param arrayAttributeName the attribute name that references the array
     * @param fieldNames the field names to include in the result file
     * @throws JsonParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void generateReducedReport(String sourceFile, String targetFile, String arrayAttributeName, String... fieldNames) throws JsonParseException, FileNotFoundException, IOException {
        // Optimize lookup using HashSet
        Set<String> fieldNameSet = new HashSet<>(Arrays.asList(fieldNames));

        // Get JsonParser
        JsonParser jsonParser = JsonStreamUtils.getJsonParser(sourceFile);

        // Find the start of the array
        if(JsonStreamUtils.findStartOfArray(jsonParser, arrayAttributeName)) {
            // Create JsonGenerator to stream output
            JsonGenerator generator = JSON_FACTORY.createGenerator(new File(targetFile), JsonEncoding.UTF8)
                    .useDefaultPrettyPrinter();

            // Write start array as a wrapper for the objects
            generator.writeStartArray();

            // The pointer will be a the start of the first object when we call jsonParser.nextToken() 
            // This approach only works for flat objects, that don't have arrays within the object structure. 
            while(jsonParser.nextToken() != null && !jsonParser.currentToken().equals(JsonToken.END_ARRAY)) {
                
                //We can simply copy start and end of object + required attributes (fieldNames)
                JsonToken token = jsonParser.currentToken();
                if(token.equals(JsonToken.START_OBJECT) || token.equals(JsonToken.END_OBJECT)) {
                    // Just copy start and end tokens
                    generator.copyCurrentEvent(jsonParser);
                } else if(token.equals(JsonToken.FIELD_NAME) && fieldNameSet.contains(jsonParser.getText())) {
                    // If the current attribute name is provided in field names we copy it over
                    generator.copyCurrentEvent(jsonParser);
                    // Then move to the next token and copy the value
                    jsonParser.nextToken();
                    generator.copyCurrentEvent(jsonParser);                   
                }
            }

            // Close the array and the generator
            generator.writeEndArray();
            generator.close();
        }
        
    }
 
    /**
     * Internal helper method to find the start of the array referenced by the given
     * arrayAttributeName.
     * @param jsonParser the {@link JsonParser} instance
     * @param arrayAttributeName the name of the attribute that references the array
     * @return true in case the array structure has been found
     * @throws IOException
     */
    private static boolean findStartOfArray(JsonParser jsonParser, String arrayAttributeName) throws IOException {
        // Move the pointer until we reach attributeName array
        boolean foundArrayAttribute = false;
        while(!foundArrayAttribute && jsonParser.nextToken() != null) {
            JsonToken token = jsonParser.currentToken();
            if(token.equals(JsonToken.FIELD_NAME)
                    && arrayAttributeName.equals(jsonParser.getText())
                    && jsonParser.nextToken().equals(JsonToken.START_ARRAY) ) {
                // we have found a field that matches the arrayAttributeName and the following
                // token is a also the start of an array
                foundArrayAttribute = true;
            }
        }
        return foundArrayAttribute;
    }

}
