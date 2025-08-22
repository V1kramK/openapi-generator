/**
 * MCP Server function for Update an existing pet
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Function;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

class Put_PetMCPTool {
    
    public static Function<MCPServer.MCPRequest, MCPServer.MCPToolResult> getPut_PetHandler(MCPServer.APIConfig config) {
        return (request) -> {
            try {
                Map<String, Object> args = request.getArguments();
                if (args == null) {
                    return new MCPServer.MCPToolResult("Invalid arguments object", true);
                }
                
                List<String> queryParams = new ArrayList<>();
        if (args.containsKey("status")) {
            queryParams.add("status=" + args.get("status"));
        }
        if (args.containsKey("name")) {
            queryParams.add("name=" + args.get("name"));
        }
        if (args.containsKey("id")) {
            queryParams.add("id=" + args.get("id"));
        }
        if (args.containsKey("category")) {
            queryParams.add("category=" + args.get("category"));
        }
        if (args.containsKey("photoUrls")) {
            queryParams.add("photoUrls=" + args.get("photoUrls"));
        }
        if (args.containsKey("tags")) {
            queryParams.add("tags=" + args.get("tags"));
        }
                
                String queryString = queryParams.isEmpty() ? "" : "?" + String.join("&", queryParams);
                String url = config.getBaseUrl() + "/api/v2/put_pet" + queryString;
                
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + config.getApiKey())
                    .header("Accept", "application/json")
                    .GET()
                    .build();
                
                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                
                if (response.statusCode() >= 400) {
                    return new MCPServer.MCPToolResult("API error: " + response.body(), true);
                }
                
                // Pretty print JSON
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.body());
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
                
                return new MCPServer.MCPToolResult(prettyJson);
                
            } catch (IOException | InterruptedException e) {
                return new MCPServer.MCPToolResult("Request failed: " + e.getMessage(), true);
            } catch (Exception e) {
                return new MCPServer.MCPToolResult("Unexpected error: " + e.getMessage(), true);
            }
        };
    }
    
    public static MCPServer.Tool createPut_PetTool(MCPServer.APIConfig config) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> statusProperty = new HashMap<>();
        statusProperty.put("type", "string");
        statusProperty.put("required", false);
        statusProperty.put("description", "Input parameter: pet status in the store");
        properties.put("status", statusProperty);
        Map<String, Object> nameProperty = new HashMap<>();
        nameProperty.put("type", "string");
        nameProperty.put("required", true);
        nameProperty.put("description", "");
        properties.put("name", nameProperty);
        Map<String, Object> idProperty = new HashMap<>();
        idProperty.put("type", "string");
        idProperty.put("required", false);
        idProperty.put("description", "");
        properties.put("id", idProperty);
        Map<String, Object> categoryProperty = new HashMap<>();
        categoryProperty.put("type", "string");
        categoryProperty.put("required", false);
        categoryProperty.put("description", "");
        properties.put("category", categoryProperty);
        Map<String, Object> photoUrlsProperty = new HashMap<>();
        photoUrlsProperty.put("type", "string");
        photoUrlsProperty.put("required", true);
        photoUrlsProperty.put("description", "");
        properties.put("photoUrls", photoUrlsProperty);
        Map<String, Object> tagsProperty = new HashMap<>();
        tagsProperty.put("type", "string");
        tagsProperty.put("required", false);
        tagsProperty.put("description", "");
        properties.put("tags", tagsProperty);
        parameters.put("properties", properties);
        
        MCPServer.ToolDefinition definition = new MCPServer.ToolDefinition(
            "put_pet",
            "Update an existing pet",
            parameters
        );
        
        return new MCPServer.Tool(definition, getPut_PetHandler(config));
    }
    
}