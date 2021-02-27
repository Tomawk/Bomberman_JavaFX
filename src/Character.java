import javafx.animation.*;
import javafx.scene.image.*;
import javafx.util.*;

public class Character {
    
    private static final int MARGINE_DX = 583, MARGINE_DW = 645, MARGINE_SX = 50, MARGINE_UP = 200; //01
    
    private final InterfacciaStatica interfaccia;
    
    public boolean goNorth, goSouth, goEast, goWest; //02
    public boolean bombPlaced, isAlive, ready;
    
    private final ImageView circle; //03
    private final ImageView bombermanLeft,bombermanRight,bombermanBack,bombermanFront,tomba;
    
    private final Bomb bomba;
    
    private final GestoreSuoni gestoreSuoni;
    
    private final Timeline countdownBomb; //04
    
    private int contatoreAnimazioneBomba; //05
   
    public Character(InterfacciaStatica interfacciaS,GestoreSuoni gestoreS){
        
        interfaccia = interfacciaS;
        isAlive = true;
        ready = false;
        gestoreSuoni = gestoreS;
        bomba = new Bomb();
        
        bombermanFront = new ImageView("file:../../AddOn/Immagini/bomberman_front.png");
        circle = new ImageView("file:../../AddOn/Immagini/dot.png");
        bombermanLeft = new ImageView("file:../../AddOn/Immagini/bomberman_left.png");
        bombermanRight = new ImageView("file:../../AddOn/Immagini/bomberman_right.png");
        bombermanBack = new ImageView("file:../../AddOn/Immagini/bomberman_back.png");
        tomba = new ImageView("file:../../AddOn/Immagini/tombstone.png");
        impostaDimensioniImageView();
        
        countdownBomb = new Timeline();
        contatoreAnimazioneBomba = 8;
        
        countdownBomb.setCycleCount(Timeline.INDEFINITE);
        countdownBomb.getKeyFrames().add(
        new KeyFrame( //06
                Duration.millis(200), 
                event -> animazioneEsplosioneBomba() 
        )
        );
    }
    
    public void impostaDimensioniImageView(){
        circle.setFitWidth(10);
        circle.setFitHeight(10);
        tomba.setFitWidth(40);
        tomba.setFitHeight(40);
        bombermanFront.setFitWidth(40);
        bombermanFront.setFitHeight(40);
        bombermanLeft.setFitWidth(40);
        bombermanLeft.setFitHeight(40);
        bombermanRight.setFitWidth(40);
        bombermanRight.setFitHeight(40);
        bombermanBack.setFitWidth(40);
        bombermanBack.setFitHeight(40);
    }
    
    public void Spawn(){ //07
        ready = true;
        double posBombermanX = MARGINE_SX + bombermanFront.getBoundsInLocal().getWidth()/2;
        double posBombermanY = MARGINE_UP + bombermanFront.getBoundsInLocal().getHeight()/2;
        double posCuoreX = MARGINE_SX + bombermanFront.getBoundsInLocal().getWidth()/2;
        double posCuoreY = MARGINE_UP + 30;
        moveHeroTo(posBombermanX, posBombermanY, posCuoreX, posCuoreY);
        interfaccia.getContenitore().getChildren().removeAll(bombermanFront,bombermanLeft,bombermanRight,bombermanBack);
        interfaccia.getContenitore().getChildren().add(bombermanFront);
    }
    
     public void moveHeroBy(int dx, int dy) { //08
        if (ready == true){
        if (dx == 0 && dy == 0) return;
        

        final double cx = bombermanFront.getBoundsInLocal().getWidth()  / 2; //09
        final double cy = bombermanFront.getBoundsInLocal().getHeight() / 2; //09
        
        final double cx1 = circle.getBoundsInLocal().getWidth()  / 2; //10
        final double cy1 = circle.getBoundsInLocal().getHeight() / 2; //10

        
        double x = cx + bombermanFront.getLayoutX() + dx; //11
        double y = cy + bombermanFront.getLayoutY() + dy; //11
        
        double x1 = cx1 + circle.getLayoutX() + dx; //12
        double y1 = cy1 + circle.getLayoutY() + dy; //12
        

        moveHeroTo(x, y, x1, y1);
        }
    }
 
    public void moveHeroTo(double x, double y, double x1, double y1) {
        if(ready == true) {
            
        final double cx = bombermanFront.getBoundsInLocal().getWidth()  / 2; //09
        final double cy = bombermanFront.getBoundsInLocal().getHeight() / 2; //09
        
        final double cx1 = circle.getBoundsInLocal().getWidth()  / 2; //10
        final double cy1 = circle.getBoundsInLocal().getHeight() / 2; //10
       
       
        if (x - cx >= MARGINE_SX &&
            x + cx <= MARGINE_DX &&
            y - cy >= MARGINE_UP &&
            y + cy <= MARGINE_DW) { //13
            bombermanFront.relocate(x - cx, y - cy);
            bombermanLeft.relocate(x - cx, y - cy);
            bombermanRight.relocate(x - cx, y - cy);
            bombermanBack.relocate(x - cx, y - cy);
            }
        
        if (x1 - cx1 >= MARGINE_SX + 15 &&
            x1 + cx1 <= MARGINE_DX - 15 &&
            y1 - cy1 >= MARGINE_UP + 25 &&
            y1 + cy1 <= MARGINE_DW - 5 ) { //13
            circle.relocate(x1 - cx1, y1 - cy1);
            }
        }
       
    }
    
    public void displayBomb(FileConfigurazioneXML config){ //14
        if(bomba.isPlaced == false) {
            interfaccia.getContenitore().getChildren().add(bomba.getBomb()); //15
            bomba.getBomb().relocate(circle.getLayoutX()-10, circle.getLayoutY()-15); //15
            bomba.isPlaced = true;
            countdownBomb.play(); //16
            GestoreLogAttivitaXML.creaLog("Bomba Piazzata", config);
        }
        
        
    }
    
    public void bombExplosion1(){ //17
        if(bomba.isExploded == false){
            interfaccia.getContenitore().getChildren().remove(bomba.getBomb()); 
            interfaccia.getContenitore().getChildren().add(bomba.getExplosion1());
            gestoreSuoni.avviaSuonoBomba();
            bomba.getExplosion1().relocate(bomba.getBomb().getLayoutX()-20, bomba.getBomb().getLayoutY()-20);
            
        }
    }
    
     public void bombExplosion2(){ //17
        if(bomba.isExploded == false){ 
            interfaccia.getContenitore().getChildren().remove(bomba.getExplosion1()); 
            interfaccia.getContenitore().getChildren().add(bomba.getExplosion2());
            bomba.getExplosion2().relocate(bomba.getBomb().getLayoutX()-20, bomba.getBomb().getLayoutY()-20);
        }
    }
    
     public void bombExplosion3(){ //17
        if(bomba.isExploded == false){
            interfaccia.getContenitore().getChildren().remove(bomba.getExplosion2());
            interfaccia.getContenitore().getChildren().add(bomba.getExplosion3());
            bomba.getRettangolo1().relocate(bomba.getBomb().getLayoutX()-20, bomba.getBomb().getLayoutY()+10);
            bomba.getRettangolo2().relocate(bomba.getBomb().getLayoutX()+10, bomba.getBomb().getLayoutY()-20);
            bomba.getExplosion3().relocate(bomba.getBomb().getLayoutX()-20, bomba.getBomb().getLayoutY()-20);
            bomba.isExploded = true;
        }
    }
     
     public void dead(){ //18
         isAlive = false;
         ready = false;
         tomba.relocate(circle.getLayoutX()-15,circle.getLayoutY()-20);
         if(interfaccia.getContenitore().getChildren().contains(bombermanLeft)) interfaccia.getContenitore().getChildren().remove(bombermanLeft);
         if(interfaccia.getContenitore().getChildren().contains(bombermanRight)) interfaccia.getContenitore().getChildren().remove(bombermanRight);
         if(interfaccia.getContenitore().getChildren().contains(bombermanFront)) interfaccia.getContenitore().getChildren().remove(bombermanFront);
         if(interfaccia.getContenitore().getChildren().contains(bombermanBack)) interfaccia.getContenitore().getChildren().remove(bombermanBack);
         interfaccia.getContenitore().getChildren().add(tomba);
     }
     
    public void animazioneEsplosioneBomba(){ //19
        if(contatoreAnimazioneBomba == 0) contatoreAnimazioneBomba = 8;
        contatoreAnimazioneBomba --;
        switch(contatoreAnimazioneBomba){
            case 4: bombExplosion1(); break;
            case 3: bombExplosion2(); break;
            case 2: bombExplosion3(); break;
            case 0:  {
            countdownBomb.stop();
            bomba.isPlaced = false;
            bomba.isExploded = false;
            interfaccia.getContenitore().getChildren().remove(bomba.getExplosion3());
            break;
            }
        }
    }
    
    public void impostaAnimazioneDirezione(){ //20
        if (goNorth && !goEast && !goWest && !goSouth) { 
            if(interfaccia.getContenitore().getChildren().contains(bombermanLeft)) interfaccia.getContenitore().getChildren().remove(bombermanLeft);
            if(interfaccia.getContenitore().getChildren().contains(bombermanRight)) interfaccia.getContenitore().getChildren().remove(bombermanRight);
            if(interfaccia.getContenitore().getChildren().contains(bombermanFront)) interfaccia.getContenitore().getChildren().remove(bombermanFront);
            if(!interfaccia.getContenitore().getChildren().contains(bombermanBack)) interfaccia.getContenitore().getChildren().add(bombermanBack);
        }
        if (!goNorth && !goEast && !goWest && goSouth) { 
            if(interfaccia.getContenitore().getChildren().contains(bombermanLeft)) interfaccia.getContenitore().getChildren().remove(bombermanLeft);
            if(interfaccia.getContenitore().getChildren().contains(bombermanRight)) interfaccia.getContenitore().getChildren().remove(bombermanRight);
            if(!interfaccia.getContenitore().getChildren().contains(bombermanFront)) interfaccia.getContenitore().getChildren().add(bombermanFront);
            if(interfaccia.getContenitore().getChildren().contains(bombermanBack)) interfaccia.getContenitore().getChildren().remove(bombermanBack);
        }
        if (goWest && !goEast)  { 
            if(!interfaccia.getContenitore().getChildren().contains(bombermanLeft)) interfaccia.getContenitore().getChildren().add(bombermanLeft);
            if(interfaccia.getContenitore().getChildren().contains(bombermanRight)) interfaccia.getContenitore().getChildren().remove(bombermanRight);
            if(interfaccia.getContenitore().getChildren().contains(bombermanFront)) interfaccia.getContenitore().getChildren().remove(bombermanFront);
            if(interfaccia.getContenitore().getChildren().contains(bombermanBack)) interfaccia.getContenitore().getChildren().remove(bombermanBack);
        }
        if (goEast && !goWest) { 
            if(interfaccia.getContenitore().getChildren().contains(bombermanLeft)) interfaccia.getContenitore().getChildren().remove(bombermanLeft);
            if(!interfaccia.getContenitore().getChildren().contains(bombermanRight)) interfaccia.getContenitore().getChildren().add(bombermanRight);
            if(interfaccia.getContenitore().getChildren().contains(bombermanFront)) interfaccia.getContenitore().getChildren().remove(bombermanFront);
            if(interfaccia.getContenitore().getChildren().contains(bombermanBack)) interfaccia.getContenitore().getChildren().remove(bombermanBack);
        }
    }
    
    public ImageView getCharacterFront() { return bombermanFront; }
    public ImageView getCircle(){ return circle; }
    public Bomb getBomb(){ return bomba; }
    public ImageView getTombstone(){ return tomba; }
}

/* Note:

01) Variabili intere per delimitare la board di gioco
02) Variabili booleane per la gestione del movimento
03) ImageView di un piccolo cerchietto, viene usato come hitbox per le collisioni che riguardano Bomberman
04) Timeline per gestire l'esplosione della bomba e la sua animazione
05) Contatore animazione per la timeline
06) Ogni 200 millisecondi viene chiamata animazioneEsplosioneBomba()
07) Funzione chiamata quando viene premuto START. Posiziona il nemico in alto a sx e può iniziare a muoversi
08) Funzione per il movimento, avviene in base ai valori di dx e dy (dx spostamento destra/sinistra) (dy spostamento alto/basso)
09) Prende le coordinate della posizione attuale di Bomberman, prendendo il punto centrale dell'ImageView
10) Prende le coordinate della posizione attuale del cerchietto, prendendo il punto centrale dell'ImageView
11) Prendo la coordinate calcolata precendentemente e ci sommo la distanza rispetto alla finesta (getLayout) e lo spostamento dx/dy
12) Prendo la coordinate calcolata precendentemente e ci sommo la distanza rispetto alla finesta (getLayout) e lo spostamento dx1/dy1
13) uso Relocate() per riposizionare l'ImageView di Bomberman e del cerchietto, se vado fuori dai margini della board il movimento non avviene
14) Funzione chiamata dall'AnimationTimer ogni volta che viene premuto SHIFT, se la bomba è già piazzata non ha effetto
15) Aggiungo la bomba allo scenario e la posiziono nei pressi di Bomberman
16) Avvio la Timeline per gestire l'esplosione
17) Funzioni chiamate dalla Timeline in base al contatoreEsplosione, realizzano l'animazione di esplosione
    andando a aggiungere e rimuovere ImageView dalla scena
18) Funzione chiamata da GestoreCollisioni, Bomberman muore e la sua ImageView viene rimpiazzata da una lapide
19) Funzione chiamata ogni 200 millisecondi dalla Timeline, in base al valore del contatore gestisce l'animazione
    mentre se il contatore vale 0 allora la Timeline verrà interrotta, verranno resettati alcuni booleani in modo
    da permettere il rischieramento della bomba.
20) Gestisce l'animazione del movimento del personaggio
*/