package ca.qc.bdeb.sim203.tp2;

import javafx.scene.image.Image;

import java.util.Random;

public class MonstreOeil extends MonstreNormal {
    protected double vitesseInitiale;

    public MonstreOeil(int niveau) {
        super(niveau);
        this.ay = 0;
        this.vitesseInitiale = genererVX(niveau);
        this.vy = 0;
        this.diametre = genererDiametreHasard();
        this.image = new Image("oeil.png", diametre, diametre, true, true);

        this.x = genererXHasard();
    }
    /**
     * Méthode pour generer la vitesse en x des monstres
     */
    @Override
    public double genererVX(int niveau) {
        return 1.3 * super.genererVX(niveau);
    }

    /**
     * Méthode pour update la physique des monstres oeils
     */
    @Override
    public void update(double dt, double tempsEcoule) {
        raffraichirPhysique(dt);
        changerDirection(tempsEcoule);
        verifierSiHorsEcran();

    }
    /**
     * Méthode pour changer la direction du mouvement des monstres oeils
     */
    public void changerDirection(double tempsEcoule) {
        if (tempsEcoule % 0.75 >= 0.5) {
            this.vx = -this.vitesseInitiale;
        } else {
            this.vx = this.vitesseInitiale;
        }
    }
    /**
     * Méthode pour generer la position en x aléatoire des monstres
     */
    @Override
    public double genererXHasard() {

        Random rnd = new Random();
        if (rnd.nextInt(2) == 0) {
            return -this.image.getWidth();
        } else {
            this.image = ImageHelper.ImageHelpers.flop(image);
            this.vitesseInitiale = -vitesseInitiale;
            return Main.WIDTH;
        }
    }

    /**
     * Méthode pour verifier lorsque le monstre traverse le milieu
     */
    @Override
    public void verifierSiTraverseMilieu() {
        if (vitesseInitiale > 0) {
            if (x > Main.WIDTH/2) {
                traverseMilieu = true;
            }
        } else if (vitesseInitiale < 0) {
            if (x + diametre < Main.WIDTH/2) {
                traverseMilieu = true;
            }
        }
    }

}
