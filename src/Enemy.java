
import javafx.scene.image.*;


public class Enemy {
    
    private static final int  MARGINE_DX = 574, MARGINE_DW = 643, MARGINE_SX = 56, MARGINE_UP = 206; //01
    
    public boolean ready, goNorth, goSouth, goEast, goWest; //02
    private boolean collisioneMuri; //03
    
    public int contatoreMovimento, previousBoolean, booleanOpposto; //04
    
    private final ImageView enemy;
    
    public Enemy(){
        
        ready = false;
        enemy = new ImageView("file:../../AddOn/Immagini/enemy1.png");
        enemy.setFitWidth(36);
        enemy.setFitHeight(36);
        previousBoolean = -1;
        booleanOpposto = -1;
        collisioneMuri = false;
        contatoreMovimento = 0;
    }
    
    
    public void moveEnemyBy(int dx, int dy) { //05
        if (ready == true){
        if (dx == 0 && dy == 0) return;
        

        final double cx = enemy.getBoundsInLocal().getWidth()  / 2;
        final double cy = enemy.getBoundsInLocal().getHeight() / 2;
        

        double x = cx + enemy.getLayoutX() + dx;
        double y = cy + enemy.getLayoutY() + dy;
        

        moveEnemyTo(x, y);
        }
    }
 
    public void moveEnemyTo(double x, double y) { //05
        if(ready == true) {
            
        final double cx = enemy.getBoundsInLocal().getWidth()  / 2;
        final double cy = enemy.getBoundsInLocal().getHeight() / 2;
       
       
        if (x - cx >= MARGINE_SX &&
            x + cx <= MARGINE_DX &&
            y - cy >= MARGINE_UP &&
            y + cy <= MARGINE_DW) {
            enemy.relocate(x - cx, y - cy);
            collisioneMuri = false;
            }
        else collisioneMuri = true;
        }
       
    }
    
    public ImageView getEnemyImageView(){ return enemy; }
    public boolean faCollisioneMuri() { return collisioneMuri; }
}

/* Note:
01) Variabili per delimitare la board di gioco
02) Variabili per il movimento
03) Variabile che va a false in caso di movimento non effettuato in quanto andrebbe fuori dalla board di gioco
04) 
    contatoreMovimento : Variabile per impedire al nemico di bloccarsi nel movimento randomico
    quando durante il movimento avviene una collisione la variabile viene incrementata
    quando supera una certa soglia (30), il nemico viene obbligato a muoversi nel verso opposto
    previousBoolean : Variabile che memorizza un intero corrispondente alla precedente direzione di movimento
    oppositeBoolean : Variabile intera corrispondente alla direzione opposta a quella del movimento precedente
05) Stesse funzioni di movimento viste in Character
*/