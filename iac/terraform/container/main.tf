resource "google_artifact_registry_repository" "teddys-corona-diaries" {
  provider = google-beta
  project = "capstone-teddys-corona-diaries"
  location = "europe-west1"
  repository_id = "capstone-teddys-corona-diaries"
  format = "DOCKER"
}
