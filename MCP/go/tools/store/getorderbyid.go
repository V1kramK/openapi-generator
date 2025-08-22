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

func GetorderbyidHandler(cfg *config.APIConfig) func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
	return func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		args, ok := request.Params.Arguments.(map[string]any)
		if !ok {
			return mcp.NewToolResultError("Invalid arguments object"), nil
		}
		orderIdVal, ok := args["orderId"]
		if !ok {
			return mcp.NewToolResultError("Missing required path parameter: orderId"), nil
		}
		orderId, ok := orderIdVal.(string)
		if !ok {
			return mcp.NewToolResultError("Invalid path parameter: orderId"), nil
		}
		url := fmt.Sprintf("%s/store/order/%s", cfg.BaseURL, orderId)
		req, err := http.NewRequest("GET", url, nil)
		if err != nil {
			return mcp.NewToolResultErrorFromErr("Failed to create request", err), nil
		}
		// Set authentication based on auth type
		// Fallback to single auth parameter
		if cfg.APIKey != "" {
			req.Header.Set("api_key", cfg.APIKey)
		}
		req.Header.Set("Accept", "application/json")

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
		var result models.Order
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

func CreateGetorderbyidTool(cfg *config.APIConfig) models.Tool {
	tool := mcp.NewTool("get_store_order_orderId",
		mcp.WithDescription("Find purchase order by ID"),
		mcp.WithNumber("orderId", mcp.Required(), mcp.Description("ID of pet that needs to be fetched")),
	)

	return models.Tool{
		Definition: tool,
		Handler:    GetorderbyidHandler(cfg),
	}
}
