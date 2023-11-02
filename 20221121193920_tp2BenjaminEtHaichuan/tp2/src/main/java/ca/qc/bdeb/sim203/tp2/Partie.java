package ca.qc.bdeb.sim203.tp2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Partie {
    protected int nbVies = 3;
    protected boolean partieFinie = false;
    protected int score = 0;
    protected int niveau = 1;
    protected Squelette squelette;
    protected ArrayList<MonstreNormal> tabMonstre = new ArrayList<>();
    protected int scorePourProchainNiveau = 5;
    protected double tempsEcouleAffichageNiveau = 0;
    protected double tempsEcoulePartieFinie = 0;
    protected double tempsEcoule = 0;
    protected int niveauDeltaTempsPasse = 1;
    final double frameRate = 10 * 1e-9;
    protected boolean fermerJeu = false;
    Image vie = ImageHelper.ImageHelpers.colorize(
            new Image("squelette.png", 30, 30, true, false), Color.PINK);

    double tempsBouleMagique = 0;
    protected ArrayList<BouleMagique> tabBouleMagique = new ArrayList<>();
    protected double tempsEcouleDepuisDernierMonstre = 0;
    protected double tempsEcouleDepuisDernierMonstreSpecial = 0;

    public Partie() {
    }

    public Partie(int niveau) {
        this.niveau = niveau;
    }

    public void commencerPartie() {
        squelette = new Squelette();

    }

    /**
     * Méthode pour verifier si un monstre est eliminer et ensuite l'éliminer s'il faut
     */
    public void eliminerMonstre(BouleMagique magie) {
        ArrayList<MonstreNormal> tabAEnlever = new ArrayList<>();
        for (MonstreNormal monstre : tabMonstre) {
            if (contactAvecMonstre(magie, monstre, magie.rayon, monstre.diametre / 2)) {
                score += 1;
                tabAEnlever.add(monstre);
            }
        }
        for (MonstreNormal monstre : tabAEnlever) {
            tabMonstre.remove(monstre);
        }
    }

    /**
     * Méthode pour la logique des vies, des niveaux et quand les monstres normales rentrent dans l'écran
     */
    public void deroulementPartie(double tempsEcoule, double deltatime) {
        ArrayList<MonstreNormal> tabAEnlever = new ArrayList<>();
        for (MonstreNormal monstre : tabMonstre) {
            if (monstre.horsDeEcran) {
                tabAEnlever.add(monstre);
                nbVies -= 1;
            }
        }
        for (MonstreNormal monstre : tabAEnlever) {
            tabMonstre.remove(monstre);
        }
        raffraichirBouleMagique(deltatime);


        if ((int) tempsEcoule % 3 == 0 && tempsEcoule - tempsEcouleDepuisDernierMonstre >= 3) {
            tabMonstre.add(new MonstreNormal(niveau));
            tempsEcouleDepuisDernierMonstre = tempsEcoule;
        }


        if ((int) tempsEcoule % 5 == 0 && niveau > 1 && tempsEcoule - tempsEcouleDepuisDernierMonstreSpecial >= 5) {
            creerMonstreSpecial();
            tempsEcouleDepuisDernierMonstreSpecial = tempsEcoule;
        }

        if (nbVies == 0) {
            partieFinie = true;
        }


        if (score == scorePourProchainNiveau) {
            niveau++;
            scorePourProchainNiveau += 5;
            tabMonstre.clear();
        }


    }

    /**
     * Méthode pour creer une instance de boule de magie
     */
    public void creerBouleMagique(double tempsEcoule) {
        if (tempsEcoule - tempsBouleMagique >= 0.6) {
            tabBouleMagique.add(new BouleMagique(squelette.x, squelette.y));
            tempsBouleMagique = tempsEcoule;
        }
    }

    /**
     * Méthode add des monstres specials dans le tableaux de monstres
     */

    public void creerMonstreSpecial() {
        if (Math.random() < 0.5) {
            tabMonstre.add(new MonstreOeil(niveau));

        } else {
            tabMonstre.add(new MonstreBouche(niveau));

        }
    }

    /**
     * Méthode pour verifier s'il a un contact entre la magie et un monstre
     */
    public boolean contactAvecMonstre(ObjetJeu magie, MonstreNormal monstre, int rayon1, int rayon2) {
        double dx = Math.pow(magie.x - monstre.x, 2);
        double dy = Math.pow(magie.y - monstre.y, 2);
        return dx + dy < (rayon1 + rayon2) * (rayon1 + rayon2);
    }

    /**
     * Méthode pour afficher le niveau au milieu de l'écran
     */
    public void afficherNiveau(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.setFont(new Font(45));

        context.setTextAlign(TextAlignment.CENTER);
        context.fillText("Niveau " + niveau, 320, 250);
    }

    /**
     * Méthode pour ajouter une vie
     */
    public void ajouterVie() {
        this.nbVies++;
    }


    /**
     * Méthode pour rafraichir les boules de magies et de les enlevers lorsqu'il faut
     */
    public void raffraichirBouleMagique(double deltaTime) {
        ArrayList<BouleMagique> tabAEnlever = new ArrayList<>();
        for (BouleMagique bouleMagique : tabBouleMagique) {
            if (bouleMagique.y + 2 * bouleMagique.rayon <= 0) {
                tabAEnlever.add(bouleMagique);
            }
        }

        for (BouleMagique bouleMagique : tabAEnlever) {
            tabBouleMagique.remove(bouleMagique);
        }
        for (BouleMagique bouleMagique : tabBouleMagique) {
            eliminerMonstre(bouleMagique);
            bouleMagique.update(deltaTime);

            bouleMagique.updateParticuleVisible(deltaTime);
        }

    }

    /**
     * Méthode pour dessiner le score et les vies dans la scene de jeu
     */
    private void dessinerInterface(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.fillText(String.valueOf(score), 320, 50);

        if (tempsEcouleAffichageNiveau <= 3) {
            afficherNiveau(context);
        }

        int positionX = 265;
        for (int i = 0; i < nbVies; i++) {
            context.drawImage(vie, positionX, 100);
            positionX += 40;
        }


    }

    /**
     * Méthode pour update les objets
     */
    public void update(double deltaTime) {

        deroulementPartie(tempsEcoule, deltaTime);
        if (niveauDeltaTempsPasse < niveau) {
            tempsEcouleAffichageNiveau = 0;

        }

        niveauDeltaTempsPasse = niveau;

        squelette.update(deltaTime);


        if (!tabMonstre.isEmpty()) {
            for (MonstreNormal monstre : tabMonstre) {
                monstre.update(deltaTime, tempsEcoule);
            }
        }

        tempsEcoule += deltaTime;
        tempsEcouleAffichageNiveau += deltaTime;


    }

    /**
     * Méthode pour dessiner les objets dans la scene jeu
     */
    public void draw(GraphicsContext context, double deltaTime, double lastTime) {
        double tempsEcouleSquelette = deltaTime + lastTime;
        context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        dessinerInterface(context);

        squelette.draw(context, tempsEcouleSquelette, frameRate);

        if (!tabMonstre.isEmpty()) {
            for (MonstreNormal monstre : tabMonstre) {
                context.drawImage(monstre.image, monstre.x, monstre.y);
            }
        }

        for (BouleMagique bouleMagique : tabBouleMagique) {

            for (ParticuleMagique particule : bouleMagique.tabParticuleVisible) {
                context.setFill(bouleMagique.color);
                context.fillOval(bouleMagique.x + bouleMagique.rayon + particule.x - particule.rayon, bouleMagique.y + bouleMagique.rayon + particule.y - particule.rayon,
                        particule.rayon * 2, particule.rayon * 2);
            }


        }
        if (partieFinie) {
            tempsEcoulePartieFinie += deltaTime;
            if (tempsEcoulePartieFinie < 3) {
                context.setFill(Color.RED);
                context.setFont(new Font(45));

                context.setTextAlign(TextAlignment.CENTER);
                context.fillText("Game Over", 320, 250);
            } else {
                fermerJeu = true;
            }

        }

    }

}
