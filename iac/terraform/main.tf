
module "storage" {
  source = ".//storage"
}

module "container" {
  source = ".//container"
  mongodburi = var.mongodburi
}