
import javafx.scene.image.*;

public class Masso {
    private final ImageView imageviewMasso;
    public boolean containsDoor; //01
    
    
    public Masso(){
        imageviewMasso = new ImageView("file:../../AddOn/Immagini/masso.png");
        imageviewMasso.setFitHeight(40);
        imageviewMasso.setFitWidth(40);
        containsDoor = false;
    }
   
    
    public ImageView getImageViewMasso(){ return imageviewMasso; }
}

//01) Booleano che stabilisce se il masso contiene la botola