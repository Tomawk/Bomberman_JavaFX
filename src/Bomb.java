
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Bomb {
    private final static int WIDTH_BOMB = 30;
    private final static int HEIGHT_BOMB = 30;
    private final static int WIDTH_EXPLOSION = 80;
    private final static int HEIGHT_EXPLOSION = 80;
    
    private final ImageView imageviewBomb,imageviewExplosion1,imageviewExplosion2,imageviewExplosion3;
    
    public boolean isExploded, isPlaced;
    
    private final Rectangle rettangoloOrizzontale, rettangoloVerticale;
    
    public Bomb(){
        imageviewBomb = new ImageView("file:../../AddOn/Immagini/bomba1.png");
        imageviewBomb.setFitHeight(HEIGHT_BOMB);
        imageviewBomb.setFitWidth(WIDTH_BOMB);
        
        imageviewExplosion1 = new ImageView("file:../../AddOn/Immagini/exp1.png");
        imageviewExplosion1.setFitHeight(HEIGHT_EXPLOSION);
        imageviewExplosion1.setFitWidth(WIDTH_EXPLOSION);
        imageviewExplosion2 = new ImageView("file:../../AddOn/Immagini/exp2.png");
        imageviewExplosion2.setFitHeight(HEIGHT_EXPLOSION);
        imageviewExplosion2.setFitWidth(WIDTH_EXPLOSION);
        
        imageviewExplosion3 = new ImageView("file:../../AddOn/Immagini/exp3.png");
        imageviewExplosion3.setFitHeight(HEIGHT_EXPLOSION);
        imageviewExplosion3.setFitWidth(WIDTH_EXPLOSION);
        isExploded = false;
        isPlaced = false;
        
        rettangoloOrizzontale = new Rectangle(100,100,80,20);
        rettangoloOrizzontale.setFill(Color.RED);
        rettangoloVerticale = new Rectangle(130,70,20,80);
        rettangoloVerticale.setFill(Color.RED);
        
    }
    
    public ImageView getBomb(){ return imageviewBomb; }
    public ImageView getExplosion1() { return imageviewExplosion1; }
    public ImageView getExplosion2() { return imageviewExplosion2; }
    public ImageView getExplosion3() { return imageviewExplosion3; }
    public Rectangle getRettangolo1() { return rettangoloOrizzontale; }
    public Rectangle getRettangolo2() { return rettangoloVerticale; }
}
