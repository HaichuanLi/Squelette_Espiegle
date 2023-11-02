package ca.qc.bdeb.sim203.tp2;

public class ParticuleMagique extends ObjetJeu {
    protected int rayon;
    protected int q = 10;
    public final int K = 30;
    public double forceEnX = 0;
    public double forceEnY = 0;

    public ParticuleMagique(double xParticule, double yParticule) {

        this.rayon = 5;
        this.x = xParticule + rayon;
        this.y = yParticule + rayon;
        this.ay = 0;
        this.vy = 0;
        this.ax = 0;
    }

    /**
     * Méthode pour update la physique de chaque particule
     */
    @Override
    public void update(double dt) {
        raffraichirPhysique(dt);
    }

    public void resetForce() {
        forceEnX = 0;
        forceEnY = 0;
    }
    /**
     * Méthode pour raffraichir la physique de chaque particule
     */
    @Override
    public void raffraichirPhysique(double dt) {
        vx += dt * forceEnX;
        vy += dt * forceEnY;
        verifierVitesse();
        x += dt * vx;
        y += dt * vy;
        if (Math.sqrt(x*x+y*y)  > 35) {
            x = -rayon;
            vx = 0;
            vy = 0;
            resetForce();
        }

    }

    /**
     * Méthode pour verifier la vitesse des particules
     */
    private void verifierVitesse() {
        if (this.vy > 50) {
            this.vy = 50;
        } else if (this.vy < -50) {
            this.vy = -50;
        }
        if (this.vx > 50) {
            this.vx = 50;
        } else if (this.vx < -50) {
            this.vx = -50;
        }
    }
    /**
     * Méthode pour update la force de chaque particule
     */
    public void updateForce(ParticuleMagique particule1, ParticuleMagique particule2) {


        double deltaX = particule1.x - particule2.x;
        double deltaY = particule1.y - particule2.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (distance < 0.01) {
            distance = 0.01;
        }
        double proportionX = deltaX / distance;
        double proportionY = deltaY / distance;
        double forceElectrique = (K * particule1.q * particule2.q) / (distance*distance);
        this.forceEnX += forceElectrique * proportionX;
        this.forceEnY += forceElectrique * proportionY;
    }


}
