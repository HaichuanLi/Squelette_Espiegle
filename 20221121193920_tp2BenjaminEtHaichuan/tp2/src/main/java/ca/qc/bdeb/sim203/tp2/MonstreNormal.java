package ca.qc.bdeb.sim203.tp2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class MonstreNormal extends ObjetJeu {
    protected ArrayList<String> tabImages = creerTabImage();
    protected int diametre;
    protected int niveau;
    protected boolean traverseMilieu;
    protected boolean horsDeEcran;

    public MonstreNormal(int niveau) {
        this.horsDeEcran = false;
        this.traverseMilieu = false;
        this.color = ImageHelper.ImageHelpers.couleurAuHasard();
        this.niveau = niveau;
        this.diametre = genererDiametreHasard();
        this.ax = 0;
        this.ay = 100;
        this.vx = genererVX(niveau);
        this.vy = genererVYhasard();
        this.y = genererYHasard();
        this.image = choisirImage(this.diametre);
        this.x = genererXHasard();
    }
    /**
     * Méthode pour update la physique des monstres normales
     */
    public void update(double dt,double tempsEcoule) {
        verifierSiHorsEcran();
        raffraichirPhysique(dt);
    }

    /**
     * Méthode pour generer la vitesse en x des monstres
     */
    public double genererVX(int niveau) {
        double vitesseX = 100 * Math.pow(niveau, 0.33) + 200;
        return vitesseX;
    }
    /**
     * Méthode pour generer la vitesse en y aléatoire des monstres
     */
    public double genererVYhasard() {
        Random rnd = new Random();
        return -(rnd.nextInt(101) + 100);
    }

    /**
     * génère un nombre entier entre 40 (inclus) et 100 (inclus) correspondant au diamètre d'un monstre.
     *
     * @return le nombre entier entre 40 (inclus) et 100 (inclus)
     */
    public int genererDiametreHasard() {
        Random rnd = new Random();
        return rnd.nextInt(61) + 40;
    }
    /**
     * Méthode pour choisir une des images du tableau de monstres
     */
    public Image choisirImage(int diametre) {
        Random rnd = new Random();
        int position = rnd.nextInt(tabImages.size());
        Image image = new Image(tabImages.get(position), diametre, diametre, true, true);
        image = ImageHelper.ImageHelpers.colorize(image, color);
        return image;
    }
    /**
     * Méthode pour creer le tableau d'image à partir des images fournies par le prof
     */
    public ArrayList<String> creerTabImage() {
        ArrayList<String> tabImage = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tabImage.add("monstres/" + i + ".png");
        }
        return tabImage;
    }
    /**
     * Méthode pour generer la position en y aléatoire des monstres
     */
    public double genererYHasard() {

        return Math.random() * (Main.HEIGHT / 5 * 4 - Main.HEIGHT / 5) + Main.HEIGHT / 5;
    }

    /**
     * Méthode pour generer la position en x aléatoire des monstres
     */
    public double genererXHasard() {

        Random rnd = new Random();
        if (rnd.nextInt(2) == 0) {
            return -this.image.getWidth();
        } else {
            this.image = ImageHelper.ImageHelpers.flop(image);
            this.vx = -vx;
            return Main.WIDTH;
        }
    }
    /**
     * Méthode pour vérifier si le monstres sort de l'écran
     */
    public void verifierSiHorsEcran() {
        verifierSiTraverseMilieu();
        if (x + image.getWidth() <= 0 && traverseMilieu) {
            horsDeEcran = true;
        } else if (x >= Main.WIDTH && traverseMilieu) {
            horsDeEcran = true;
        }
    }
    /**
     * Méthode pour verifier lorsque le monstre est rendu au milieu de l'écran
     */
    public void verifierSiTraverseMilieu() {
        if (vx > 0) {
            if (x > Main.WIDTH / 2) {
                traverseMilieu = true;
            }
        } else if (vx < 0) {
            if (x + diametre < Main.WIDTH / 2) {
                traverseMilieu = true;
            }
        }
    }



}
