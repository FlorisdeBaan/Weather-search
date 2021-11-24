package WeerApp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIWeer implements Initializable {
    ActueelWeer actueelWeer;
    String plaatsnaamSet;


    @FXML
    private Label plaatsnaam, temperatuur, dag, omschrijving, waarschuwing, windSnelheid, bewolkingsgraad, luchtdruk, luchtvochtigheid;
    @FXML
    private ImageView afbeelding;
    @FXML
    private JFXButton Verander, Kies, Annuleer;
    @FXML
    private JFXTextField Plaatsnaam, onzichtbaar;


    //Event Handler voor elke knop onder in het scherm
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent ae) {
        String initialCity = plaatsnaam.getText(); //Opslaan van de laatstgekozen plaatsnaam

        if(ae.getSource() == Verander){
            Plaatsnaam.setText("");
            bottomSet(true);
            Plaatsnaam.requestFocus();
        }else if (ae.getSource() == Kies) {
            setPressed();
        } else if (ae.getSource() == Annuleer) {
            Plaatsnaam.setText(initialCity);
            bottomSet(false);
            onzichtbaar.requestFocus();
        }
    }

    //Een constructor om bij het opstarten alvast gegevens van een voor geselecteerde stad in te laden
    public GUIWeer() { this.plaatsnaamSet = "Roosendaal".toUpperCase();}

    //Methode om alle velden leeg te maken
    private void reset() {
        bottomSet(false);
        dag.setText("");
        temperatuur.setText("");
        omschrijving.setText("");
        windSnelheid.setText("");
        bewolkingsgraad.setText("");
        luchtdruk.setText("");
        luchtvochtigheid.setText("");
        afbeelding.setImage(null);
    }

    //Methode om een nieuwe plaats in te voeren
    private void setPressed(){
        //Als de gebruiker geen plaatsnaam invult
        if(Plaatsnaam.getText().equals("")){
            showToast("Plaatsnaam mag niet leeg zijn");
        }else {
            try {
                waarschuwing.setText("");
                this.plaatsnaamSet = Plaatsnaam.getText().trim();
                Plaatsnaam.setText((Plaatsnaam.getText().trim()).toUpperCase());
                actueelWeer = new ActueelWeer(plaatsnaamSet);
                toonWeer();
                bottomSet(false);
                onzichtbaar.requestFocus();
            }catch(Exception e){
                plaatsnaam.setText("Waarschuwing!");
                plaatsnaam.setTextFill(Color.BLACK);
                showToast("Plaatsnaam kan niet worden gevonden");
                reset();
                onzichtbaar.requestFocus();
            }
        }
    }

    //methode om de zichtbaarheid van de knoppen onder in het scherm aan te passen
    private void bottomSet(boolean statement){
        Plaatsnaam.setDisable(!statement);
        Kies.setVisible(statement);
        Verander.setVisible(!statement);
        Annuleer.setVisible(statement);
    }

    //methode om waarschuwingen te laten zien
    private void showToast(String message) {
        waarschuwing.setText(message);
        waarschuwing.setTextFill(Color.GOLD);
        waarschuwing.setStyle("-fx-background-color: #fff; -fx-background-radius: 50px;");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), waarschuwing);
        fadeIn.setToValue(1);
        fadeIn.setFromValue(0);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.play();
            pause.setOnFinished(event2 -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), waarschuwing);
                fadeOut.setToValue(0);
                fadeOut.setFromValue(1);
                fadeOut.play();
            });
        });
    }

    //methode om de weergegevens op te halen en op het scherm te zetten
    private void toonWeer(){
        actueelWeer.getActueelWeer();
        plaatsnaam.setText(actueelWeer.getPlaatsnaam().toUpperCase());
        temperatuur.setText(actueelWeer.getTemperatuur().toString()+"°C");
        dag.setText(actueelWeer.getDag().toUpperCase());
        omschrijving.setText(actueelWeer.getOmschrijving().toUpperCase());
        afbeelding.setImage(new Image(AfbeeldingSwitch.getImage(actueelWeer.getIcoon())));
        windSnelheid.setText(actueelWeer.getWindSnelheid()+" m/s");
        bewolkingsgraad.setText(actueelWeer.getBewolkingsgraad()+"%");
        luchtdruk.setText(actueelWeer.getLuchtdruk()+" hpa");
        luchtvochtigheid.setText(actueelWeer.getLuchtvochtigheid()+"%");
    }

    public void initialize(URL location, ResourceBundle resources) {
        Plaatsnaam.setText(plaatsnaamSet);
        Plaatsnaam.setDisable(true);
        Kies.setVisible(false);
        Annuleer.setVisible(false);
        waarschuwing.setText("");
        actueelWeer = new ActueelWeer(plaatsnaamSet);
        onzichtbaar.requestFocus();

        //methode om te zien of er een interrnetverbinding is en elk veld op het scherm uit te schakelen
        try{
            toonWeer();
        } catch (Exception e){
            plaatsnaam.setText("Waarschuwing!");
            plaatsnaam.setTextFill(Color.BLACK);
            showToast("Geen internetverbinding");
            reset();
            Verander.setDisable(true);
            Plaatsnaam.setText("");
        }

        //Event handler om de plaatsnaam te setten als enter wordt ingedrukt op het toetsenbord
        Plaatsnaam.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                setPressed();
            }
        });
    }
}