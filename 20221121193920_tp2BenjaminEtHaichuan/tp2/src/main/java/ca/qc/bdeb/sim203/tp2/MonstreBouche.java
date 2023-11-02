package ca.qc.bdeb.sim203.tp2;

import javafx.scene.image.Image;


public class MonstreBouche extends MonstreNormal {
    protected double yBase;
    public MonstreBouche(int niveau) {
        super(niveau);
        this.yBase=this.y;
        this.ay = 0;
        this.vx = genererVX(niveau);
        this.vy = 0;
        this.diametre = genererDiametreHasard();
        this.image = new Image("bouche.png", diametre, diametre, true, true);
        this.x = genererXHasard();

    }

    /**
     * Méthode pour update la physique des monstres bouches
     */
    @Override
    public void update(double dt, double tempsEcoule) {
        raffraichirPhysique(dt);
        osciller(tempsEcoule);
        verifierSiHorsEcran();

    }
    /**
     * Méthode pour que le monstre bouche a un mouvement d'oscillation
     */
    private void osciller(double tempsEcoule) {
        y = yBase + 50 * Math.sin(10 * tempsEcoule);
    }
}
