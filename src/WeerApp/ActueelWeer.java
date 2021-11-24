package WeerApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ActueelWeer {
    private final String plaatsnaam;
    private String dag;
    private Integer temperatuur;
    private String icoon;
    private String omschrijving;
    private String windSnelheid;
    private String bewolkingsgraad;
    private String luchtdruk;
    private String luchtvochtigheid;


    //Getters voor de bovenstaande attributen
    public String getPlaatsnaam() {
        return plaatsnaam;
    }

    public String getDag() {
        return dag;
    }

    public Integer getTemperatuur() {
        return temperatuur;
    }

    public String getIcoon() {
        return icoon;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String getWindSnelheid() {
        return windSnelheid;
    }

    public String getBewolkingsgraad() {
        return bewolkingsgraad;
    }

    public String getLuchtdruk() {
        return luchtdruk;
    }

    public String getLuchtvochtigheid() {
        return luchtvochtigheid;
    }

    public ActueelWeer(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }



    //Bouw een string op vanuit het JSON verzoek
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //Haal het JSON object op
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    //Methode om actuele weergegevens op te halen vanuit een zelf in te vullen plaatsnaam
    public void getActueelWeer(){
        int d = 0;

        JSONObject json;
        JSONObject json_specific; //haal specifieke gegevens op uit het JSON Object

        SimpleDateFormat hd1 = new SimpleDateFormat("EEEE"); //geef de dag als output
        Calendar c = Calendar.getInstance();

        //Maak een verbinding met de API via een URL en API Key
        try {
            json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q="+ plaatsnaam +"&appid=API-KEY-OPENWEATHER&lang=nl&units=metric");
        } catch (IOException e) {
            return;
        }

        //Ontvang de gevraagde JSON data en sla deze op in attributen
        json_specific = json.getJSONObject("main");
        this.temperatuur = json_specific.getInt("temp");
        this.luchtdruk = json_specific.get("pressure").toString();
        this.luchtvochtigheid = json_specific.get("humidity").toString();
        json_specific = json.getJSONObject("wind");
        this.windSnelheid = json_specific.get("speed").toString();
        json_specific = json.getJSONObject("clouds");
        this.bewolkingsgraad = json_specific.get("all").toString();

        c.add(Calendar.DATE, d);
        this.dag = hd1.format(c.getTime());

        json_specific = json.getJSONArray("weather").getJSONObject(0);
        this.omschrijving = json_specific.get("description").toString();
        this.icoon = json_specific.get("icon").toString();
    }
}

