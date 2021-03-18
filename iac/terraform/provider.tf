provider "google" {

  project = "capstone-teddys-corona-diaries"
  region  = "europe-west1"
  zone    = "europe-west1-b"

}

terraform {
  backend "gcs" {
    credentials = "/tmp/credentials.json"
    bucket = "teddys-corona-diaries-terraform"
    prefix = "terraform/state"
  }
}