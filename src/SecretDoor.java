
import java.util.*;
import javafx.scene.image.*;


public class SecretDoor {
    public boolean isFound; //01
    private final ImageView door;
    private final int randomIndex; //02
    
    public SecretDoor(int lenght){
        isFound = false;
        door = new ImageView("file:../../AddOn/Immagini/trapdoor.png");
        door.setFitWidth(40);
        door.setFitHeight(40);
        Random randX = new Random();
        randomIndex = randX.nextInt(lenght);
    }
    
    public ImageView getImageViewDoor() { return door; }
    public int getRandomIndexDoor(){ return randomIndex; }
}

/* Note:

01) Variabile booleana che va a true non appena la botola viene rivelata
02) Indice randomico da 0 a lenght dove lenght è il numero di massi che possono contenere la botola
*/