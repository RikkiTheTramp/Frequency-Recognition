package sample;

import javafx.scene.media.AudioEqualizer;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

/**
 * Klasse die een track representeert
 */
public class Track {

    //gain van de EQ boost
    private static final double gain = 12;
    //Q-factor van de Equalizer
    private static final double Q = 2.5;

    //ondergrens van het spectrum
    private static final double ondergrens = 30;
    //bovengrens van het spectrum
    private static final double bovengrens = 16000;
    //nodige logaritme
    private static final double log = Math.log(bovengrens/ondergrens)/Math.log(2);

    //RandomGenerator
    private static final Random rg = new Random();

    private final String name;
    private final MediaPlayer mp;
    private final AudioEqualizer ae;
    private boolean isPlaying;

    /**
     * Constructor
     * @param file de audiofile
     */
    public Track(File file){
        name = file.getName();
        mp = new MediaPlayer(new Media(file.toURI().toString()));
        mp.setAutoPlay(true);
        isPlaying = true;

        ae = mp.getAudioEqualizer();
        ae.getBands().clear();
        ae.getBands().add(getEqBand());

        for (EqualizerBand e: ae.getBands()) {
            System.out.println(e.getCenterFrequency() + " " + e.getBandwidth());
        }

        ae.setEnabled(false);
    }

    @Override
    public String toString(){
        return name;
    }

    /**
     * Laat de track afspelen als hij op pauze staat
     * Laat de track pauzeren als hij aan het afspelen is
     */
    public void playPause() {
        if (isPlaying) {
            mp.pause();
        }else {
            mp.play();
        }
        isPlaying = !isPlaying;
    }

    /**
     * Stopt het afspelen van de track
     */
    public void stop() {
        mp.stop();
    }

    /**
     * Geeft een nieuwe EQ-band terug
     * @return een nieuwe EQ-band met een random centerfrequentie
     */
    private EqualizerBand getEqBand() {
        double freq = getFreq();
        return new EqualizerBand(freq, freq /Q, gain);
    }

    /**
     * Geeft een random frequentie terug in het frequentiegebied tssn {@link #ondergrens} en {@link #bovengrens}
     * @return een random frequentie
     */
    private double getFreq(){
        //2^(log2(bovengrens/ondergrens)) * ondergrens
        return Math.round(Math.pow(2, rg.nextDouble() * log) * ondergrens);
    }

    /**
     * zet de Equalizer aan
     */
    public void eqOn() {
        ae.setEnabled(true);
    }

    /**
     * zet de Equalizer uit
     */
    public void eqOff() {
        ae.setEnabled(false);
    }

    /**
     * maakt een nieuwe EQ-band aan (en vervangt de vorige)
     */
    public void nextEQ() {
        ae.getBands().set(0, getEqBand());
    }
}
