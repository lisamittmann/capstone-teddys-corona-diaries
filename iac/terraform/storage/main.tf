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

resource "google_storage_bucket_iam_member" "get-images" {
  bucket = google_storage_bucket.teddys-corona-diaries-images.name
  role = "roles/storage.legacyObjectReader"
  member = "allUsers"
}