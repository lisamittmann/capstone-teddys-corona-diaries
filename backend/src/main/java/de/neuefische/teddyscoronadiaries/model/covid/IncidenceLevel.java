package de.neuefische.teddyscoronadiaries.model.covid;

public enum IncidenceLevel {
    GREEN(35),
    YELLOW(50),
    ORANGE(100),
    RED(1000000000);

    public final int label;

    private IncidenceLevel(int label) {
        this.label = label;
    }

    public static IncidenceLevel determineIncidenceLevel(int label) {
        for (IncidenceLevel level : values()) {
            if(label <= level.label) {
                return level;
            }
        }
        return null;
    }
}
