
module "storage" {
  source = "./storage"
}

module "container" {
  source = "./container"
  mongodburi = var.mongodburi
  openweatherkey = var.openweatherkey
  googleauthclientid = var.googleauthclientid
}