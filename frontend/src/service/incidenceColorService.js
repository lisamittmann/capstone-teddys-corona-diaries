export const getColorCode = (incidenceLevel) => {
    if (incidenceLevel === "GREEN") {return "var(--color-incidence-green)"}
    if (incidenceLevel === "YELLOW") {return "var(--color-incidence-yellow)"}
    if (incidenceLevel === "ORANGE") {return"var(--color-incidence-orange)"}
    if (incidenceLevel === "RED") {return"var(--color-incidence-red)"}
    else {return"var(--color-incidence-silver)"}
}
