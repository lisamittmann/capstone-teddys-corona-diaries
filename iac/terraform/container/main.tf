resource "google_cloudbuild_trigger" "build-trigger" {
  project = "capstone-teddys-corona-diaries"

  filename = "cloudbuild.yml"

  github {
    name = "lisamittmann/capstone-teddys-corona-diaries"
    push {
      branch =".*"
    }
  }
}