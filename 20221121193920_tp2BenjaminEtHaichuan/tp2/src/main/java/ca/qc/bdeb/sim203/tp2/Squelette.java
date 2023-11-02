package ca.qc.bdeb.sim203.tp2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Squelette extends ObjetJeu {

    protected int graviteSquelette = 1200;
    protected Image[] frames = new Image[]{
            new Image("squelette/marche1.png",48,96,true,true),
            new Image("squelette/marche2.png",48,96,true,true)
    };
    protected double derniereValeurX;
    protected boolean positionVersDroite = true;

    public Squelette() {
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = graviteSquelette;
        this.x=Main.WIDTH/2;
        this.y=Main.HEIGHT/2;
        this.w = 48;
        this.h = 96;
        this.image = new Image("squelette/stable.png",w,h,true,true);
    }


    public Image[] getFrames() {
        return frames;
    }
    /**
     * Méthode pour update la physique du squelette
     */
    @Override
    public void update(double dt){
        raffraichirPhysique(dt);

        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean jump = Input.isKeyPressed(KeyCode.UP);


        if (left) {
            ax = -1000;
            positionVersDroite = false;
        }

        else if (right) {
            ax = 1000;
            positionVersDroite = true;
        }

        else {
            ax = 0;
            int signeVitesse = vx > 0 ? 1 : -1;
            double vitesseAmortissementX = -signeVitesse * 500;
            vx += dt * vitesseAmortissementX;
            int nouveauSigneVitesse = vx > 0 ? 1 : -1;

            if(nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }
        }

        if(vx > 300) vx = 300;

        else if(vx < -300) vx = -300;

        if (jump && vy == 0) {
            vy = -600;
            ay = graviteSquelette;
        }


        if (this.y + h >= Main.HEIGHT) {
            vy = 0;
            ay = 0;
            y = Main.HEIGHT - h -1 ;
        }
        if (this.x + w >= Main.WIDTH) {
            vx = 0;
            x = Main.WIDTH - w;
        }
        if (this.x <= 0) {
            x = 0;
        }

    }
    /**
     * Méthode pour dessiner le squelette
     */
    public void draw(GraphicsContext context, double tempsEcouleSquelette, double frameRate){
        if (derniereValeurX == x) {
            context.drawImage(verifierInverser(image), x, y);
        } else {
            context.drawImage(verifierInverser(imageMouvement(tempsEcouleSquelette, frameRate)), x, y);

        }
        derniereValeurX = x;
    }
    /**
     * Méthode pour selectionner entre les deux images de mouvements
     */
    public Image imageMouvement(double tempsEcouleSqueltte, double frameRate){
        int frame = (int) Math.floor(tempsEcouleSqueltte * frameRate);
        return getFrames()[frame%getFrames().length];
    }
    /**
     * Méthode pour verifier si le squelette doit etre inverser ou non
     */
    public Image verifierInverser(Image image){
        if (!positionVersDroite){
            return ImageHelper.ImageHelpers.flop(image);
        }
        else {return image;}
    }











}
