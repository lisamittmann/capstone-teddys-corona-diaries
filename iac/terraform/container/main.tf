resource "google_artifact_registry_repository" "teddys-corona-diaries" {
  provider = google-beta
  project = "capstone-teddys-corona-diaries"
  location = "europe-west1"
  repository_id = "capstone-teddys-corona-diaries"
  format = "DOCKER"
}

resource "google_cloudbuild_trigger" "build-trigger" {
  project = "capstone-teddys-corona-diaries"
  name = "capstone-build-trigger"
  description = "Trigger used to build image for Teddys Corona Diaries"
  github {
    owner = "lisamittmann"
    name = "capstone-teddys-corona-diaries"
    push {
      branch =".*"
    }
  }

  filename = "Dockerfile"
}