package de.neuefische.teddyscoronadiaries.model.weather;

public enum WeatherCategories {
    SN("Schnee"),
    SL("Schneeregen"),
    H("Hagel"),
    T("Gewitter"),
    HR("Starkregen"),
    LR("Leichter Regen"),
    S("Regen"),
    HC("Bewölkt"),
    LC("Leicht bewölkt"),
    C("Sonnig");

    public final String name;

    private WeatherCategories(String name) {this.name = name;}

    public String getName(WeatherCategories category) {
        for(WeatherCategories weatherCategory : values()) {
            if(category.equals(weatherCategory)) {return weatherCategory.name;}
        }
        return null;
    }
}
