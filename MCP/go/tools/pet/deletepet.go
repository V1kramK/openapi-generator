package tools

import (
	"context"
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/swagger-petstore/mcp-server/config"
	"github.com/swagger-petstore/mcp-server/models"
	"github.com/mark3labs/mcp-go/mcp"
)

func DeletepetHandler(cfg *config.APIConfig) func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
	return func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		args, ok := request.Params.Arguments.(map[string]any)
		if !ok {
			return mcp.NewToolResultError("Invalid arguments object"), nil
		}
		petIdVal, ok := args["petId"]
		if !ok {
			return mcp.NewToolResultError("Missing required path parameter: petId"), nil
		}
		petId, ok := petIdVal.(string)
		if !ok {
			return mcp.NewToolResultError("Invalid path parameter: petId"), nil
		}
		url := fmt.Sprintf("%s/pet/%s", cfg.BaseURL, petId)
		req, err := http.NewRequest("DELETE", url, nil)
		if err != nil {
			return mcp.NewToolResultErrorFromErr("Failed to create request", err), nil
		}
		// Set authentication based on auth type
		if cfg.BearerToken != "" {
			req.Header.Set("Authorization", fmt.Sprintf("Bearer %s", cfg.BearerToken))
		}
		req.Header.Set("Accept", "application/json")
		if val, ok := args["api_key"]; ok {
			req.Header.Set("api_key", fmt.Sprintf("%v", val))
		}

		resp, err := http.DefaultClient.Do(req)
		if err != nil {
			return mcp.NewToolResultErrorFromErr("Request failed", err), nil
		}
		defer resp.Body.Close()

		body, err := io.ReadAll(resp.Body)
		if err != nil {
			return mcp.NewToolResultErrorFromErr("Failed to read response body", err), nil
		}

		if resp.StatusCode >= 400 {
			return mcp.NewToolResultError(fmt.Sprintf("API error: %s", body)), nil
		}
		// Use properly typed response
		var result map[string]interface{}
		if err := json.Unmarshal(body, &result); err != nil {
			// Fallback to raw text if unmarshaling fails
			return mcp.NewToolResultText(string(body)), nil
		}

		prettyJSON, err := json.MarshalIndent(result, "", "  ")
		if err != nil {
			return mcp.NewToolResultErrorFromErr("Failed to format JSON", err), nil
		}

		return mcp.NewToolResultText(string(prettyJSON)), nil
	}
}

func CreateDeletepetTool(cfg *config.APIConfig) models.Tool {
	tool := mcp.NewTool("delete_pet_petId",
		mcp.WithDescription("Deletes a pet"),
		mcp.WithString("api_key", mcp.Description("")),
		mcp.WithNumber("petId", mcp.Required(), mcp.Description("Pet id to delete")),
	)

	return models.Tool{
		Definition: tool,
		Handler:    DeletepetHandler(cfg),
	}
}
