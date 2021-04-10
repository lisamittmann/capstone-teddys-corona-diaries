resource "google_artifact_registry_repository" "teddys-corona-diaries" {
  provider = google-beta
  project = "capstone-teddys-corona-diaries"
  location = "europe-west1"
  repository_id = "capstone-teddys-corona-diaries"
  format = "DOCKER"
}

resource "google_cloud_run_service" "cloud-run-service" {
  name = "corona-diaries"
  location = "europe-west1"

  template {
    spec {
      containers {
        image = "gcr.io/capstone-teddys-corona-diaries/teddys-corona-diaries"
        env {
          name = "spring.data.mongodb.uri"
          value = var.mongodburi
        }
        env {
          name = "openweather.key"
          value= var.openweatherkey
        }
        env {
          name = "google.auth.client-id"
          value= var.googleauthclientid
        }
        env {
          name = "security.jwt.secret"
          value = var.jwtsecret
        }
        ports {
          container_port = "8080"
        }
        resources {
          limits = {
            cpu = "2000m"
            memory = "512Mi"
          }
        }
      }
    }
  }

  metadata {
    annotations = {
      "autoscaling.knative.dev/maxScale" = "4"
      "run.googleapis.com/launch-stage" = "BETA"
      "client.knative.dev/user-image" = "gcr.io/capstone-teddys-corona-diaries/teddys-corona-diaries"
      "run.googleapis.com/client-name" = "gcloud"
      "run.googleapis.com/client-version" = "332.0.0"
      "run.googleapis.com/ingress" = "all"
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }

  autogenerate_revision_name = true
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location = google_cloud_run_service.cloud-run-service.location
  project = "capstone-teddys-corona-diaries"
  service = google_cloud_run_service.cloud-run-service.name

  policy_data = data.google_iam_policy.noauth.policy_data
}