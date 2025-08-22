/**
 * MCP Server function for Updated user
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

class Put_User_UsernameMCPTool {
    
    public static Function<MCPServer.MCPRequest, MCPServer.MCPToolResult> getPut_User_UsernameHandler(MCPServer.APIConfig config) {
        return (request) -> {
            try {
                Map<String, Object> args = request.getArguments();
                if (args == null) {
                    return new MCPServer.MCPToolResult("Invalid arguments object", true);
                }
                
                List<String> queryParams = new ArrayList<>();
        if (args.containsKey("username")) {
            queryParams.add("username=" + args.get("username"));
        }
        if (args.containsKey("password")) {
            queryParams.add("password=" + args.get("password"));
        }
        if (args.containsKey("phone")) {
            queryParams.add("phone=" + args.get("phone"));
        }
        if (args.containsKey("email")) {
            queryParams.add("email=" + args.get("email"));
        }
        if (args.containsKey("firstName")) {
            queryParams.add("firstName=" + args.get("firstName"));
        }
        if (args.containsKey("lastName")) {
            queryParams.add("lastName=" + args.get("lastName"));
        }
        if (args.containsKey("userStatus")) {
            queryParams.add("userStatus=" + args.get("userStatus"));
        }
        if (args.containsKey("id")) {
            queryParams.add("id=" + args.get("id"));
        }
                
                String queryString = queryParams.isEmpty() ? "" : "?" + String.join("&", queryParams);
                String url = config.getBaseUrl() + "/api/v2/put_user_username" + queryString;
                
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
    
    public static MCPServer.Tool createPut_User_UsernameTool(MCPServer.APIConfig config) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> usernameProperty = new HashMap<>();
        usernameProperty.put("type", "string");
        usernameProperty.put("required", true);
        usernameProperty.put("description", "name that need to be deleted");
        properties.put("username", usernameProperty);
        Map<String, Object> passwordProperty = new HashMap<>();
        passwordProperty.put("type", "string");
        passwordProperty.put("required", false);
        passwordProperty.put("description", "");
        properties.put("password", passwordProperty);
        Map<String, Object> phoneProperty = new HashMap<>();
        phoneProperty.put("type", "string");
        phoneProperty.put("required", false);
        phoneProperty.put("description", "");
        properties.put("phone", phoneProperty);
        Map<String, Object> emailProperty = new HashMap<>();
        emailProperty.put("type", "string");
        emailProperty.put("required", false);
        emailProperty.put("description", "");
        properties.put("email", emailProperty);
        Map<String, Object> firstNameProperty = new HashMap<>();
        firstNameProperty.put("type", "string");
        firstNameProperty.put("required", false);
        firstNameProperty.put("description", "");
        properties.put("firstName", firstNameProperty);
        Map<String, Object> lastNameProperty = new HashMap<>();
        lastNameProperty.put("type", "string");
        lastNameProperty.put("required", false);
        lastNameProperty.put("description", "");
        properties.put("lastName", lastNameProperty);
        Map<String, Object> userStatusProperty = new HashMap<>();
        userStatusProperty.put("type", "string");
        userStatusProperty.put("required", false);
        userStatusProperty.put("description", "Input parameter: User Status");
        properties.put("userStatus", userStatusProperty);
        Map<String, Object> idProperty = new HashMap<>();
        idProperty.put("type", "string");
        idProperty.put("required", false);
        idProperty.put("description", "");
        properties.put("id", idProperty);
        parameters.put("properties", properties);
        
        MCPServer.ToolDefinition definition = new MCPServer.ToolDefinition(
            "put_user_username",
            "Updated user",
            parameters
        );
        
        return new MCPServer.Tool(definition, getPut_User_UsernameHandler(config));
    }
    
}