
import javafx.scene.*;
import javafx.scene.image.*;

public class Roccia {
    private final ImageView imageviewRoccia;
    
    public Roccia(){
        imageviewRoccia = new ImageView("file:../../AddOn/Immagini/roccia2.png");
        imageviewRoccia.setFitHeight(40);
        imageviewRoccia.setFitWidth(40);
    }
    
    public Node getImageViewRoccia(){ return imageviewRoccia; }
}
