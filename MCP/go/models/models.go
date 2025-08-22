package models

import (
	"context"
	"github.com/mark3labs/mcp-go/mcp"
)

type Tool struct {
	Definition mcp.Tool
	Handler    func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error)
}

// Order represents the Order schema from the OpenAPI specification
type Order struct {
	Petid int64 `json:"petId,omitempty"`
	Quantity int `json:"quantity,omitempty"`
	Shipdate string `json:"shipDate,omitempty"`
	Status string `json:"status,omitempty"` // Order Status
	Complete bool `json:"complete,omitempty"`
	Id int64 `json:"id,omitempty"`
}

// Pet represents the Pet schema from the OpenAPI specification
type Pet struct {
	Name string `json:"name"`
	Photourls []string `json:"photoUrls"`
	Status string `json:"status,omitempty"` // pet status in the store
	Tags []Tag `json:"tags,omitempty"`
	Category Category `json:"category,omitempty"`
	Id int64 `json:"id,omitempty"`
}

// Tag represents the Tag schema from the OpenAPI specification
type Tag struct {
	Name string `json:"name,omitempty"`
	Id int64 `json:"id,omitempty"`
}

// User represents the User schema from the OpenAPI specification
type User struct {
	Userstatus int `json:"userStatus,omitempty"` // User Status
	Username string `json:"username,omitempty"`
	Email string `json:"email,omitempty"`
	Firstname string `json:"firstName,omitempty"`
	Id int64 `json:"id,omitempty"`
	Lastname string `json:"lastName,omitempty"`
	Password string `json:"password,omitempty"`
	Phone string `json:"phone,omitempty"`
}

// ApiResponse represents the ApiResponse schema from the OpenAPI specification
type ApiResponse struct {
	TypeField string `json:"type,omitempty"`
	Code int `json:"code,omitempty"`
	Message string `json:"message,omitempty"`
}

// Category represents the Category schema from the OpenAPI specification
type Category struct {
	Id int64 `json:"id,omitempty"`
	Name string `json:"name,omitempty"`
}
