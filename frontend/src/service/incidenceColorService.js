export const getTheme = (incidenceDetails) => {
    let theme
    switch (incidenceDetails.incidenceLevel) {
        case "GREEN":
            theme = {main: "var(--color-incidence-green)"}
            break
        case "YELLOW":
            theme = {main: "var(--color-incidence-yellow)"}
            break
        case "ORANGE":
            theme = {main: "var(--color-incidence-orange)"}
            break
        case "RED":
            theme = {main: "var(--color-incidence-red)"}
            break
        default:
            theme = {main: "var(--color-silver)"}
    }
}
