/**
 * MCP Server function for Deletes a pet
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

class Delete_Pet_Pet_IdMCPTool {
    
    public static Function<MCPServer.MCPRequest, MCPServer.MCPToolResult> getDelete_Pet_Pet_IdHandler(MCPServer.APIConfig config) {
        return (request) -> {
            try {
                Map<String, Object> args = request.getArguments();
                if (args == null) {
                    return new MCPServer.MCPToolResult("Invalid arguments object", true);
                }
                
                List<String> queryParams = new ArrayList<>();
        if (args.containsKey("api_key")) {
            queryParams.add("api_key=" + args.get("api_key"));
        }
        if (args.containsKey("petId")) {
            queryParams.add("petId=" + args.get("petId"));
        }
                
                String queryString = queryParams.isEmpty() ? "" : "?" + String.join("&", queryParams);
                String url = config.getBaseUrl() + "/api/v2/delete_pet_pet_id" + queryString;
                
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
    
    public static MCPServer.Tool createDelete_Pet_Pet_IdTool(MCPServer.APIConfig config) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> api_keyProperty = new HashMap<>();
        api_keyProperty.put("type", "string");
        api_keyProperty.put("required", false);
        api_keyProperty.put("description", "");
        properties.put("api_key", api_keyProperty);
        Map<String, Object> petIdProperty = new HashMap<>();
        petIdProperty.put("type", "string");
        petIdProperty.put("required", true);
        petIdProperty.put("description", "Pet id to delete");
        properties.put("petId", petIdProperty);
        parameters.put("properties", properties);
        
        MCPServer.ToolDefinition definition = new MCPServer.ToolDefinition(
            "delete_pet_pet_id",
            "Deletes a pet",
            parameters
        );
        
        return new MCPServer.Tool(definition, getDelete_Pet_Pet_IdHandler(config));
    }
    
}