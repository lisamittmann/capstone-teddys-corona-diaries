resource "google_storage_bucket" "teddys-corona-diaries-images" {
  name     = "teddys-corona-diaries-images"
  location = "EU"
  project  = "capstone-teddys-corona-diaries"
}

resource "google_storage_bucket_access_control" "public_access_rule" {
  bucket = google_storage_bucket.teddys-corona-diaries-images.name
  role   = "READER"
  entity = "allUsers"
}