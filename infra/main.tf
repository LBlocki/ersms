# https://learn.microsoft.com/en-us/azure/azure-sql/database/single-database-create-terraform-quickstart?view=azuresql&tabs=azure-cli
resource "random_pet" "rg_name" {
  prefix = var.resource_group_name_prefix
}

resource "azurerm_resource_group" "rg" {
  name     = random_pet.rg_name.id
  location = var.resource_group_location
}

resource "random_pet" "azurerm_mssql_server_name" {
  prefix = "sql"
}

resource "random_password" "admin_password" {
  count       = var.admin_password == null ? 1 : 0
  length      = 20
  special     = true
  min_numeric = 1
  min_upper   = 1
  min_lower   = 1
  min_special = 1
}

locals {
  admin_password = try(random_password.admin_password[0].result, var.admin_password)
}

# Database
resource "azurerm_mssql_server" "server" {
  name                         = random_pet.azurerm_mssql_server_name.id
  resource_group_name          = azurerm_resource_group.rg.name
  location                     = azurerm_resource_group.rg.location
  administrator_login          = var.admin_username
  administrator_login_password = local.admin_password
  version                      = "12.0"
}

resource "azurerm_mssql_database" "db" {
  name      = var.sql_db_name
  server_id = azurerm_mssql_server.server.id
}

# Email
resource "azurerm_email_communication_service" "email-cs" {
  name                = "ersms-email-cs"
  resource_group_name = azurerm_resource_group.rg.name
  data_location       = "Europe"
}

# Service bus
resource "azurerm_servicebus_namespace" "ersms_servicebus" {
  name                = "ersms-servicebus"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  sku                 = "Standard"
}

# Event grid
# resource "azurerm_eventgrid_topic" "ersms_eventgrid_topic" {
#   name                = "ersms-eventgrid-topic"
#   location            = azurerm_resource_group.rg.location
#   resource_group_name = azurerm_resource_group.rg.name
# }

# resource "azurerm_servicebus_queue" "notifications" {
#   name                = "notifications"
#   namespace_name      = azurerm_servicebus_namespace.ersms_servicebus.name
#   resource_group_name = azurerm_resource_group.rg.name
# }

# resource "azurerm_servicebus_namespace_authorization_rule" "ersms_servicebus_auth_rule" {
#   name                = "ersms-servicebus-auth-rule"
#   namespace_name      = azurerm_servicebus_namespace.ersms_servicebus.name
#   resource_group_name = azurerm_resource_group.rg.name
#   listen              = true
#   send                = true
#   manage              = false
# }

# Function app
# resource "azurerm_storage_account" "ersms_notification_function_storage_account" {
#   name                     = "ersmsfunctionsa"
#   resource_group_name      = azurerm_resource_group.rg.name
#   location                 = azurerm_resource_group.rg.location
#   account_tier             = "Standard"
#   account_replication_type = "LRS"
# }

# resource "azurerm_service_plan" "ersms_service_plan" {
#   name                = "ersms-service-plan"
#   location            = azurerm_resource_group.rg.location
#   resource_group_name = azurerm_resource_group.rg.name
#   os_type             = "Linux"
#   sku_name            = "Y1"
# }

# resource "azurerm_linux_function_app" "ersms_notification_function" {
#   name                       = "ersms-notification-function"
#   location                   = azurerm_resource_group.rg.location
#   resource_group_name        = azurerm_resource_group.rg.name
#   app_service_plan_id        = azurerm_app_service_plan.ersms_notification_function_app_service_plan.id
#   storage_account_name       = azurerm_storage_account.ersms_notification_function_storage_account.name
#   storage_account_access_key = azurerm_storage_account.ersms_notification_function_storage_account.primary_access_key
#   version                    = "~4"
#   python_version             = "~3.8"
#   app_settings = {
#     "FUNCTIONS_WORKER_RUNTIME"   = "python"
#     "AzureWebJobsStorage"        = azurerm_storage_account.ersms_notification_function_storage_account.primary_connection_string
#     "ServiceBusConnectionString" = azurerm_servicebus_namespace.ersms_servicebus.default_primary_connection_string
#   }
# }