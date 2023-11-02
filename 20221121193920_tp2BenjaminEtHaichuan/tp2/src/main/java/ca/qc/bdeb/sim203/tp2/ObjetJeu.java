package ca.qc.bdeb.sim203.tp2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class ObjetJeu {


    protected double x, y;
    protected double vx,vy;
    protected double ay,ax;
    protected double w, h;
    protected Color color;

    protected Image image;

    public ObjetJeu() {

    }
    /**
     * Méthode pour raffraichir la vitesse et la position de chaque objet
     */
    public void raffraichirPhysique(double dt){
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;
    }

    /**
     * Méthode pour update la physique de chaque objet
     */
    public void update(double dt) {
        raffraichirPhysique(dt);
    }


}
