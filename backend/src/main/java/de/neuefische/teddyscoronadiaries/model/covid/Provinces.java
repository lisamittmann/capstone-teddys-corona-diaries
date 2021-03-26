package de.neuefische.teddyscoronadiaries.model.covid;

import java.util.ArrayList;
import java.util.List;

public enum Provinces {
    BW("Baden-Württemberg"),
    BY("Bayern"),
    BE("Berlin"),
    BB("Brandenburg"),
    HB("Bremen"),
    HH("Hamburg"),
    HE("Hessen"),
    MV("Mecklenburg-Vorpommern"),
    NI("Niedersachsen"),
    NW("Nordrhein-Westfalen"),
    RP("Rheinland-Pfalz"),
    SL("Saarland"),
    SN("Sachsen"),
    ST("Sachsen-Anhalt"),
    SH("Schleswig-Holstein"),
    TH("Thüringen");

    public final String name;

    private Provinces(String name){this.name = name;}

    public static List<String> getProvincesNames(){
        List<String> listOfProvinces = new ArrayList<>();
        for (Provinces province : values()) {
            listOfProvinces.add(province.name);
        }
        return listOfProvinces;
    }
}
