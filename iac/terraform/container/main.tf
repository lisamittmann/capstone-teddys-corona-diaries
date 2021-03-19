resource "google_cloudbuild_trigger" "build-trigger" {
  filename = "cloudbuild.yml"

  github {
    name = "capstone-teddys-corona-diaries"
    push {
      branch =".*"
    }
  }
}