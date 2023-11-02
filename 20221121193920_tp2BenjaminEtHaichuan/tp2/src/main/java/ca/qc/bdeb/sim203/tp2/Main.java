package ca.qc.bdeb.sim203.tp2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;



public class Main extends Application {

    private Stage stage;
    public static final double WIDTH = 640, HEIGHT = 480;

    private Partie partie = new Partie();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.stage = primaryStage;


        stage.setTitle("Squelette Espegiele");
        stage.setScene(sceneAccueil());
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("squelette.png"));

    }
    /**
     * Méthode pour changer la scene à la scene d'accueil
     *
     * @return la scene d'accueil
     */
    private Scene sceneAccueil() {
        var root = new VBox();
        root.setStyle("-fx-background-color: black;");
        root.setSpacing(10);
        Scene sceneAcceuil = new Scene(root, WIDTH, HEIGHT);
        Image imgLogo = new Image("logo.png");

        ImageView logo = new ImageView(imgLogo);
        logo.setFitWidth(480);
        logo.setPreserveRatio(true);


        root.setAlignment(Pos.CENTER);
        var btnJouer = new Button("Jouer!");
        btnJouer.setOnAction((e) -> {
            stage.setScene(sceneJouer());
        });

        var btnInfo = new Button("Infos");
        btnInfo.setOnAction((e) -> {
            stage.setScene(sceneInfos());
        });
        root.getChildren().addAll(logo, btnJouer, btnInfo);

        sceneAcceuil.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        }));

        return sceneAcceuil;


    }
    /**
     * Méthode pour changer à la scene de jeu
     * Aussi il à le handle très simple qui appele les méthodes update et draw de la classe partie
     *
     * @return la scene de jeu
     */
    private Scene sceneJouer() {
        var pane = new StackPane();
        var sceneJeu = new Scene(pane, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        var context = canvas.getGraphicsContext2D();


        partie.commencerPartie();
        pane.setStyle("-fx-background-color: black;");
        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) * 1e-9;
                partie.update(deltaTime);
                partie.draw(context, deltaTime, lastTime);
                lastTime = now;

                if(partie.fermerJeu) {
                    this.stop();
                    stage.setScene(sceneAccueil());
                    resetPartie(1);
                }
            }
        };


        pane.getChildren().add(canvas);


        timer.start();


        sceneJeu.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.SPACE) {
                partie.creerBouleMagique(partie.tempsEcoule);

            }
            if (e.getCode() == KeyCode.H) {
                partie.niveau++;
                System.out.println("Niveau ajouté!");
                partie.scorePourProchainNiveau ++;
            }
            if (e.getCode() == KeyCode.J) {
                partie.score++;
                System.out.println("Point ajouté!");
            }
            if (e.getCode() == KeyCode.K) {
                partie.ajouterVie();
                System.out.println("Vie ajoutée!");
            }
            if (e.getCode() == KeyCode.L) {
                partie.nbVies=0;
            }

            else {
                Input.setKeyPressed(e.getCode(), true);
                System.out.println(e.getCode());
            }

        });
        sceneJeu.setOnKeyReleased((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                stage.setScene(sceneAccueil());
                resetPartie(0);
            } else {
                Input.setKeyPressed(e.getCode(), false);
            }
        });


        return sceneJeu;
    }

    /**
     * Méthode pour changer à la scene d'infos
     *
     * @return la scene de jeu
     */

    private Scene sceneInfos() {
        var root = new VBox();
        Scene sceneInfos = new Scene(root, WIDTH, HEIGHT);
        var titre = new Text("Squelette Espiègle");
        titre.setFont(Font.font(38));

        var textHaichuan = new HBox();
        var textePar = new Text("Par");
        var nomColore1 = new Text("Haichuan Li");
        nomColore1.setFill(ImageHelper.ImageHelpers.couleurAuHasard());
        textePar.setFont(Font.font(20));
        nomColore1.setFont(Font.font(24));
        textHaichuan.getChildren().addAll(textePar, nomColore1);
        textHaichuan.setAlignment(Pos.CENTER);
        textHaichuan.setSpacing(5);

        var textBenjamin = new HBox();
        var texteEt = new Text("et");
        var nomColore2 = new Text("Benjamin Gurlekian");
        nomColore2.setFill(ImageHelper.ImageHelpers.couleurAuHasard());
        texteEt.setFont(Font.font(20));
        nomColore2.setFont(Font.font(24));
        textBenjamin.getChildren().addAll(texteEt, nomColore2);
        textBenjamin.setAlignment(Pos.CENTER);
        textBenjamin.setSpacing(5);


        var texteExplicatifGroupe = new VBox();
        var texteExplicatif1 = new Text("Travais remis à Nicolas Hurtubise. Graphismes adaptés de hhtps://games-icons.net/");
        texteExplicatif1.setFont(Font.font(12));
        var texteExplicatif2 = new Text("Développé dans le cadre du cours 420-203-RE-Développement de programmes dans");
        texteExplicatif2.setFont(Font.font(12));
        var texteExplicatif3 = new Text("un environnement graphique, au Collège de Bois-de-Boulogne");
        texteExplicatif3.setFont(Font.font(12));
        texteExplicatifGroupe.getChildren().addAll(texteExplicatif1, texteExplicatif2, texteExplicatif3);
        texteExplicatifGroupe.setTranslateX(100);


        var btnRetour = new Button("Retour");
        btnRetour.setOnAction((event -> {
            stage.setScene(sceneAccueil());
        }));
        root.getChildren().addAll(titre, textHaichuan, textBenjamin, texteExplicatifGroupe, btnRetour);
        root.setAlignment(Pos.CENTER);

        sceneInfos.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(sceneAccueil());
            }
        }));


        return sceneInfos;
    }


    /**
     * Méthode pour reset la partie et les variables de temps pour la partie
     *
     */
    public void resetPartie(int niveau) {
        partie.tempsEcoule = 0;
        partie.tempsEcouleAffichageNiveau = 0;
        partie.tempsEcoulePartieFinie = 0;
        partie=new Partie(niveau);
    }




}
