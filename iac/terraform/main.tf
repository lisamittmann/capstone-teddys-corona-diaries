resource "google_storage_bucket" "teddys-corona-diaries-images" {
  name     = "teddys-corona-diaries-images"
  location = "EU"
  project  = "capstone-teddys-corona-diaries"
}