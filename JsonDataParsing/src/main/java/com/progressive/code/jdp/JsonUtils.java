package com.progressive.code.jdp;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	/**
	 * Parses the given string and returns the corresponding {@link JsonNode} representation.
	 * @param jsonAsString the JSON data as String
	 * @return the corresponding {@link JsonNode}
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static JsonNode getJsonStringAsNode(String jsonAsString) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readTree(jsonAsString);
	}

	/**
	 * Looks up the field name in the given {@link JsonNode}. The method will return the first
	 * occurrence of the field name. Thus, most of the time it is better to
	 * use {@link #getNodeByPath(JsonNode, String)} when the entire path is known.
	 * @param jsonNode the {@link JsonNode} to search within
	 * @param fieldname the name of the field to search for
	 * @return an {@link Optional} of type {@link JsonNode}
	 */
	public static Optional<JsonNode> getNodeByName(JsonNode jsonNode, String fieldname) {
	    JsonNode node = jsonNode.findPath(fieldname);
	    if(node.isMissingNode()) {
	        return Optional.empty();
	    }
	    return Optional.of(node);
	}

	/**
	 * Looks up a {@link JsonNode} based on the given path. The path expression has to start with
	 * "/" and each sub-path ahs to be separated with "/" as well. Example: "/personalInfo/dateOfBirth".
	 * @param jsonNode the {@link JsonNode} to search within
	 * @param path the path expression, e.g. "/personalInfo/dateOfBirth"
	 * @return an {@link Optional} of type {@link JsonNode}
	 */
   public static Optional<JsonNode> getNodeByPath(JsonNode jsonNode, String path) {
       JsonNode node = jsonNode.at(path);
       if(node.isMissingNode()) {
           return Optional.empty();
       }
       return Optional.of(node);
    }
 
    /**
     * Looks up and returns the string value of the {@link JsonNode} specified by the path.
     * @param jsonNode the root {@link JsonNode}
     * @param path the path to the {@link JsonNode} to get the string value for
     * @return an {@link Optional} of type {@link JsonNode}
     */
	public static Optional<String> getValueAsString(JsonNode jsonNode, String path) {
	    Optional<JsonNode> textNode = getNodeByPath(jsonNode, path);
	    if(textNode.isPresent() && textNode.get().isTextual()) {
	        return Optional.of(textNode.get().asText());
	    }
	    return Optional.empty();
	}

	/**
	 * Creates or updates the string value of an element described by path.
	 * @param jsonNode the root {@link JsonNode}
	 * @param path the path to the {@link JsonNode} to set the string value for
	 * @param stringValue the value t oset
	 * @return true if the value could be set
	 */
	public static boolean setOrUpdateTextValue(JsonNode jsonNode, String path, String stringValue) {
	    // Extract parentPath and attribute name, given the path variable looks like
	    // /root/sub/sub/attributeName
        int lastSeparator = path.lastIndexOf("/");
        String attributeName = path.substring(lastSeparator+1);
        JsonNode parentNode = getNodeByPath(jsonNode, path.substring(0, lastSeparator)).orElse(null);

        // We have to ensure that the parent node exist and that the parent node is an object
        if(parentNode == null || !parentNode.isObject()) {
	        return false;
	    }

        // If the attribute already exists, we have to first remove it
        if(parentNode.hasNonNull(attributeName)) {
	        ((ObjectNode)parentNode).remove(attributeName);
	    }
        // Add the attribute and it's value
        ((ObjectNode)parentNode).put(attributeName, stringValue);
	    return true;
	}
	
	public static Optional<ArrayNode> getArrayNode(JsonNode jsonNode, String path) {
	    Optional<JsonNode> arrayNode = getNodeByPath(jsonNode, path);
	    if(!arrayNode.isPresent() || !arrayNode.get().isArray()) {
	        return Optional.empty();
	    }
        return Optional.of((ArrayNode)arrayNode.get());
	}
}
