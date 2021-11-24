package WeerApp;

public class AfbeeldingSwitch {

    //klasse om de juiste afbeeldingen op te halen a.d.h.v. de icon id vanuit het JSONObject van OpenWeather

    public static String getImage(String icon) {
        switch (icon){
            case "01d":
                return "/WeerApp/afbeeldingen/Zon_D.png";
            case "01n":
                return "/WeerApp/afbeeldingen/Maan_N.png";
            case "02d":
                return "/WeerApp/afbeeldingen/LBewolkt_D.png";
            case "02n":
                return "/WeerApp/afbeeldingen/LBewolkt_N.png";
            case "03d":
                return "/WeerApp/afbeeldingen/HBewolkt_D.png";
            case "03n":
                return "/WeerApp/afbeeldingen/HBewolkt_N.png";
            case "04d": case "04n":
                return "/WeerApp/afbeeldingen/ZBewolkt.png";
            case "09d": case "09n":
                return "/WeerApp/afbeeldingen/LRegen.png";
            case "10d":
                return "/WeerApp/afbeeldingen/HRegen_D.png";
            case "10n":
                return "/WeerApp/afbeeldingen/HRegen_N";
            case "11n": case "11d":
                return "/WeerApp/afbeeldingen/Onweer.png";
            case "13d": case "13n":
                return "/WeerApp/afbeeldingen/Sneeuw.png";
            case "50d": case "50n":
                return "/WeerApp/afbeeldingen/Mist.png";
        }
        return "WeerApp/afbeeldingen/dag.png";
    }

}
