package com.progressive.code.jdp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonUtilsTest {

	@Test
	public void testJsonStringToNode() throws JsonMappingException, JsonProcessingException {
		JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
		assertNotNull(node);
	}

	@Test
	public void testGetNodeByName() throws JsonMappingException, JsonProcessingException {
	    JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
        // Scenario 1: dateOfBirth should be found
        JsonNode dateOfBirth = JsonUtils.getNodeByName(node, "dateOfBirth").orElse(null);
        assertNotNull(dateOfBirth);
        assertEquals("1996-10-18", dateOfBirth.asText());
        // Scenario 2: middle name should be null
        JsonNode middleName = JsonUtils.getNodeByName(node, "middleName").orElse(null);
        assertTrue(middleName.isNull());
        // Scenario 3: email from array returns first value
        JsonNode email = JsonUtils.getNodeByName(node, "email").orElse(null);
        assertEquals("personal.email@example.com", email.asText());
	}

    @Test
    public void testGetNodeByPath() throws JsonMappingException, JsonProcessingException {
        JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
        // Scenario 1: path to an existing node
        assertEquals("1996-10-18", JsonUtils.getNodeByPath(node, "/personalInfo/dateOfBirth").get().textValue());
        // Scenario 2: path to a non-existing node
        assertFalse(JsonUtils.getNodeByPath(node, "/personalInfo/address/street").isPresent());
    }

	@Test
	public void testGetValueAsString() throws JsonMappingException, JsonProcessingException {
	    JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
	    assertEquals("John", JsonUtils.getValueAsString(node, "/firstName").get());
	    assertFalse(JsonUtils.getValueAsString(node, "/active").isPresent());
	}

	@Test
	public void testSetTextValue() throws JsonMappingException, JsonProcessingException {
	    JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
        // Scenario 1: test with nested path
        assertTrue(JsonUtils.setOrUpdateTextValue(node, "/personalInfo/dateOfBirth", "2000-01-01"));
        assertEquals("2000-01-01", JsonUtils.getNodeByPath(node, "/personalInfo/dateOfBirth").get().textValue());
        // Scenario 2: test with attribute at root level
        assertTrue(JsonUtils.setOrUpdateTextValue(node, "/lastName", "Smith"));
        assertEquals("Smith", JsonUtils.getNodeByPath(node, "/lastName").get().textValue());
        // Scenario 3: test with non-existing path
        assertFalse(JsonUtils.setOrUpdateTextValue(node, "/personalInfo/address/street", "Main Street"));
	}

	@Test
	public void testGetArrayNode() throws JsonMappingException, JsonProcessingException {
	    JsonNode node = JsonUtils.getJsonStringAsNode(getSimpleJsonString());
	    ArrayNode arrayNode = JsonUtils.getArrayNode(node, "/personalInfo/emails").get();
        assertEquals("personal.email@example.com", arrayNode.get(0).get("email").asText());
        assertTrue(arrayNode.get(0).get("active").asBoolean());
        assertEquals("business.email@example.com", arrayNode.get(1).get("email").asText());
        assertFalse(arrayNode.get(1).get("active").asBoolean());
	}

	public String getSimpleJsonString() {
	    return """
	        {
	          "firstName": "John",
	          "lastName": "Doe",
	          "middleName": null,
	          "score": 8.7,
	          "active": true,
	          "personalInfo": {
	            "dateOfBirth": "1996-10-18",
	            "emails": [
	              {
	                "email": "personal.email@example.com",
	                "type": "personal",
	                "active": true
	              },
                  {
                    "email": "business.email@example.com",
                    "type": "business",
                    "active": false
                  }
	            ]
	          }
	        }
	        """;
	}

}
