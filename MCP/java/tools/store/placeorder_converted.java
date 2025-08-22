/**
 * MCP Server function for Place an order for a pet
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

class Post_Store_OrderMCPTool {
    
    public static Function<MCPServer.MCPRequest, MCPServer.MCPToolResult> getPost_Store_OrderHandler(MCPServer.APIConfig config) {
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
        if (args.containsKey("shipDate")) {
            queryParams.add("shipDate=" + args.get("shipDate"));
        }
        if (args.containsKey("id")) {
            queryParams.add("id=" + args.get("id"));
        }
        if (args.containsKey("petId")) {
            queryParams.add("petId=" + args.get("petId"));
        }
        if (args.containsKey("quantity")) {
            queryParams.add("quantity=" + args.get("quantity"));
        }
        if (args.containsKey("complete")) {
            queryParams.add("complete=" + args.get("complete"));
        }
                
                String queryString = queryParams.isEmpty() ? "" : "?" + String.join("&", queryParams);
                String url = config.getBaseUrl() + "/api/v2/post_store_order" + queryString;
                
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
    
    public static MCPServer.Tool createPost_Store_OrderTool(MCPServer.APIConfig config) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> statusProperty = new HashMap<>();
        statusProperty.put("type", "string");
        statusProperty.put("required", false);
        statusProperty.put("description", "Input parameter: Order Status");
        properties.put("status", statusProperty);
        Map<String, Object> shipDateProperty = new HashMap<>();
        shipDateProperty.put("type", "string");
        shipDateProperty.put("required", false);
        shipDateProperty.put("description", "");
        properties.put("shipDate", shipDateProperty);
        Map<String, Object> idProperty = new HashMap<>();
        idProperty.put("type", "string");
        idProperty.put("required", false);
        idProperty.put("description", "");
        properties.put("id", idProperty);
        Map<String, Object> petIdProperty = new HashMap<>();
        petIdProperty.put("type", "string");
        petIdProperty.put("required", false);
        petIdProperty.put("description", "");
        properties.put("petId", petIdProperty);
        Map<String, Object> quantityProperty = new HashMap<>();
        quantityProperty.put("type", "string");
        quantityProperty.put("required", false);
        quantityProperty.put("description", "");
        properties.put("quantity", quantityProperty);
        Map<String, Object> completeProperty = new HashMap<>();
        completeProperty.put("type", "string");
        completeProperty.put("required", false);
        completeProperty.put("description", "");
        properties.put("complete", completeProperty);
        parameters.put("properties", properties);
        
        MCPServer.ToolDefinition definition = new MCPServer.ToolDefinition(
            "post_store_order",
            "Place an order for a pet",
            parameters
        );
        
        return new MCPServer.Tool(definition, getPost_Store_OrderHandler(config));
    }
    
}