package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    /**
     * publieke velden die gebruikt worden in de FXML-file
     */
    public Label trackLabel;
    public Button playPause;
    public ToggleButton eqOff;
    public ToggleButton eqOn;

    /**
     * private fields
     */
    private ToggleGroup tg;
    private Track track;

    /**
     * De constructor
     */
    public Controller(){

    }

    /**
     * De initialize methode wordt aangeroepen na de constructor en na de aanmaak van de Nodes
     */
    public void initialize(){
        //maakt nieuwe toggle groep en steekt de buttons erin
        tg = new ToggleGroup();
        eqOff.setToggleGroup(tg);
        eqOn.setToggleGroup(tg);

        //dubbelklik op het label om een nieuwe track te openen
        trackLabel.setOnMouseClicked(this::clickOnTrackLabel);

    }

    /**
     * Opent een nieuwe track
     * @param event het MouseEvent die klikte
     */
    private void clickOnTrackLabel(MouseEvent event) {
        if (event.getClickCount() > 1){
            //eerst keuzescherm openen, daarna pas huidige track stoppen met afspelen
            File file = new FileChooser().showOpenDialog(null);
            //als je geen file selecteert, moet je hem ook niet wijzigen
            if (file != null) {
                //als er nog geen track is, kan je hem niet stoppen
                if (track != null) {
                    track.stop();
                }
                track = new Track(file);
                trackLabel.setText(track.toString());
            }
        }
    }

    /**
     * Zet de EQ uit
     * opgeroepen vanuit de FXML-file
     */
    public void turnEqOff() {
        track.eqOff();
    }

    /**
     * Zet de EQ aan
     * opgeroepen vanuit de FXML-file
     */
    public void turnEqOn() {
        track.eqOn();
    }

    /**
     * Laat de track afspelen als hij op pauze staat
     * Laat de track pauzeren als hij aan het afspelen is
     */
    public void playPause() {
        track.playPause();
    }

    /**
     * Past een andere randomgegenereerde EQ toe
     */
    public void nextEQ() {
        track.nextEQ();
    }
}
