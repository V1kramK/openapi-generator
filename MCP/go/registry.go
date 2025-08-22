package main

import (
	"github.com/swagger-petstore/mcp-server/config"
	"github.com/swagger-petstore/mcp-server/models"
	tools_user "github.com/swagger-petstore/mcp-server/tools/user"
	tools_pet "github.com/swagger-petstore/mcp-server/tools/pet"
	tools_store "github.com/swagger-petstore/mcp-server/tools/store"
)

func GetAll(cfg *config.APIConfig) []models.Tool {
	return []models.Tool{
		tools_user.CreateLoginuserTool(cfg),
		tools_user.CreateDeleteuserTool(cfg),
		tools_user.CreateGetuserbynameTool(cfg),
		tools_user.CreateUpdateuserTool(cfg),
		tools_pet.CreateFindpetsbytagsTool(cfg),
		tools_pet.CreateDeletepetTool(cfg),
		tools_pet.CreateGetpetbyidTool(cfg),
		tools_user.CreateCreateuserswithlistinputTool(cfg),
		tools_user.CreateLogoutuserTool(cfg),
		tools_pet.CreateAddpetTool(cfg),
		tools_pet.CreateUpdatepetTool(cfg),
		tools_store.CreatePlaceorderTool(cfg),
		tools_user.CreateCreateuserswitharrayinputTool(cfg),
		tools_pet.CreateFindpetsbystatusTool(cfg),
		tools_store.CreateGetinventoryTool(cfg),
		tools_user.CreateCreateuserTool(cfg),
		tools_store.CreateDeleteorderTool(cfg),
		tools_store.CreateGetorderbyidTool(cfg),
	}
}
