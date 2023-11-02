package ca.qc.bdeb.sim203.tp2;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    // Code obtenu des notes de cours JavaFX – Animations IV

    // On **associe** chaque touche à vrai/faux, selon si on a
// appuyé dessus
    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();
    // Personnage.update() va demander si certaines touches sont
    // appuyées ou non
    public static boolean isKeyPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }
    // Dans le `Main`, on va écouter les événements sur la scène
// et modifier l'état
    public static void setKeyPressed(KeyCode code, boolean isPressed) {touches.put(code, isPressed);

    }
}
