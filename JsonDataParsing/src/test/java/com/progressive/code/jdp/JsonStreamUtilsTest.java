package com.progressive.code.jdp;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonStreamUtilsTest {

    private static final String SOURCE_FILE = "./data/customer-data.json";

    private static final String TARGET_FILE = "./data/output.json";

    private static final String ARRAY_ATTRIBUTE_NAME = "records";

    @Test
    public void testParseJsonAsStream() throws JsonParseException, FileNotFoundException, IOException {
        JsonParser jsonParser = JsonStreamUtils.getJsonParser(SOURCE_FILE);
        assertFalse(jsonParser.isClosed());
        while(jsonParser.nextToken() != null) {
            JsonToken token = jsonParser.currentToken();
            System.out.println(String.format("Token type: %s value: %s", token.name(), jsonParser.getText()));
        }
    }

    @Test
    public void testCountArrayItems() throws JsonParseException, FileNotFoundException, IOException {
        Long amountOfRecords = JsonStreamUtils.countArrayItems(SOURCE_FILE, ARRAY_ATTRIBUTE_NAME);
        assertEquals(4, amountOfRecords);
    }

    @Test
    public void testcountActiveCustomers() throws JsonParseException, FileNotFoundException, IOException {
        Long amountOfActiveCustomers = JsonStreamUtils.countByBooleanCondition(SOURCE_FILE, ARRAY_ATTRIBUTE_NAME, "active", true);
        assertEquals(3, amountOfActiveCustomers);
    }

    @Test
    public void testGenerateSalesPerCustomerId() throws JsonParseException, FileNotFoundException, IOException {
        JsonStreamUtils.generateReducedReport(SOURCE_FILE, TARGET_FILE, ARRAY_ATTRIBUTE_NAME, "customerId", "totalSales");
    }
}
