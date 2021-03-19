resource "google_cloudbuild_trigger" "build-trigger" {
  filename = "cloudbuild.yml"

  github {
    name = "lisamittmann/capstone-teddys-corona-diaries"
    push {
      branch =".*"
    }
  }
}