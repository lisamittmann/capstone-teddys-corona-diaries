# Teddys Corona Diaries

![starterpage](ressources/teddys_corona_diaries.png)

Lockdown has been hard for all of us, in some way or another. When restaurants closed down for the first time last year
we all were forced to feed ourselves with whatever the fridge had to offer. Also, after binging Netflix for the first
two weeks, we were hunting for some socially distanced hobbies and activities. 

Kitchen hero Teddy used this past year to
document his quarantine adventures home alone with recipes. To share the highs and lows of lockdown life his adventures
can now be found online. When restaurants close yet again Teddy's corona diaries is supposed to offer you inspiration,
comfort as well as anecdotes you can probably relate to. Additionally, you will find details on the current incidence
level in your province and some ideas for corona friendly activities while we all wait for lockdown to maybe end
someday.

### Features

- Infrastructure design in terraform for GCP container registry, cloud:run and a GCP storage bucket for image upload
- Deployment pipeline to build frontend as well as backend, create a container image using [JIB](https://github.com/GoogleContainerTools/jib) and deploy directly to cloud:run
- Backend using Spring Boot to define multiple endpoints to access data either from MongoDB or integrated public APIs
- Frontend created in React with a mobile first design 


### Integrated external APIs

- [Covid19](https://documenter.getpostman.com/view/10808728/SzS8rjbc) to retrieve historic incidence values
- [RKI API](https://npgeo-corona-npgeo-de.hub.arcgis.com/datasets/dd4580c810204019a7b8eb3e0b329dd6_0) to retrieve current aggregated incidence levels for German provinces
- [OpenWeather](https://openweathermap.org/api) to retrieve current weather condition in German province capitals

