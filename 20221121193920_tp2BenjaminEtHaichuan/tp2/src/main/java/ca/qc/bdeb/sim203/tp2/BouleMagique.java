package ca.qc.bdeb.sim203.tp2;



import java.util.ArrayList;
import java.util.Random;

public class BouleMagique extends ObjetJeu {
    protected int rayon;

    protected ArrayList<ParticuleMagique> tabParticuleCirconference = new ArrayList<>();
    protected ArrayList<ParticuleMagique> tabParticuleVisible = new ArrayList<>();


    public BouleMagique(double posSqueletteX, double posSqueletteY) {
        this.color = ImageHelper.ImageHelpers.couleurAuHasard();
        this.rayon = 35;
        this.vx = 0;
        this.vy = -300;
        this.ay = 0;
        this.ax = 0;
        this.x = posSqueletteX;
        this.y = posSqueletteY;
        creerParticuleCirconference();
        creerParticuleVisible();

    }

    /**
     * Méthode pour update la physique
     */
    public void update(double dt) {
        super.update(dt);

    }

    /**
     * Méthode pour creer la circonference des particules
     */
    public void creerParticuleCirconference() {

        double xParticule, yParticule;
        double angle;
        for (int i = 0; i < 100; i++) {
            angle = (2 * Math.PI) / 100 * (i + 1);
            xParticule = Math.cos(angle) * this.rayon;
            yParticule = Math.sin(angle) * this.rayon;
            tabParticuleCirconference.add(new ParticuleMagique(xParticule, yParticule));
        }

    }

    /**
     * Méthode pour creer les 15 particules visibles
     */
    public void creerParticuleVisible() {
        double xParticule, yParticule;
        Random rnd = new Random();
        for (int i = 0; i < 15; i++) {
            xParticule = rnd.nextInt(16);
            if (Math.random() >= 0.5) {
                xParticule = -xParticule;
            }
            yParticule = rnd.nextInt(16);
            if (Math.random() >= 0.5) {
                yParticule = -yParticule;
            }
            tabParticuleVisible.add(new ParticuleMagique(xParticule, yParticule));

        }

    }

    /**
     * Méthode pour update les forces des particules
     */
    public void updateParticuleVisible(double deltatime) {
        for (ParticuleMagique particuleVisible : tabParticuleVisible) {

            for (ParticuleMagique particuleCirconference : tabParticuleCirconference) {
                particuleVisible.updateForce(particuleVisible, particuleCirconference);
            }
            for (ParticuleMagique particuleVisible2 : tabParticuleVisible) {
                if (tabParticuleVisible.indexOf(particuleVisible2) != tabParticuleVisible.indexOf(particuleVisible)) {
                    particuleVisible.updateForce(particuleVisible, particuleVisible2);
                }
            }

            particuleVisible.update(deltatime);
            particuleVisible.resetForce();
        }
    }


}
