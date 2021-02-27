import java.util.*;
import javafx.scene.shape.*;

public class InterfacciaDinamica {
    
    private final InterfacciaStatica interfaccia;
    private final ArrayList<Rectangle> arrayRettangoliMasso;
    private final ArrayList<Masso> arrayMassi;
    private final ArrayList<Enemy> arrayNemici;
    private GestoreCoordinateRandom gestoreCoordinate;
    private int quantiMassi,quantiNemici;
    private SecretDoor portaSegreta;
    private int[][] arrayCoordinateMassi, arrayCoordinateNemici;
    private final GestoreMessaggi gestoreMessaggi;
    
    public InterfacciaDinamica(InterfacciaStatica interfacciaStatica){
        interfaccia = interfacciaStatica;
        gestoreMessaggi = new GestoreMessaggi();
        arrayRettangoliMasso = new ArrayList<>();
        arrayMassi = new ArrayList<>();
        arrayNemici = new ArrayList<>();
        
    }
    
    public void impostaCoordinate(String difficolta){ //01
        
        if(difficolta.equals("FACILE")) { quantiMassi = 20; quantiNemici = 3; }
        if(difficolta.equals("INTERMEDIO")) { quantiMassi = 30; quantiNemici = 5; }
        if(difficolta.equals("DIFFICILE")) { quantiMassi = 40; quantiNemici = 7;}
        
        gestoreCoordinate = new GestoreCoordinateRandom(quantiMassi,quantiNemici);
        arrayCoordinateMassi =  gestoreCoordinate.getArrayMassi();
        arrayCoordinateNemici =  gestoreCoordinate.getArrayNemici();
    }
    
    public void posizionaMassi(){ //02
        
        portaSegreta = new SecretDoor(quantiMassi);
        for(int k = 0; k < quantiMassi; k++){
           Masso masso = new Masso();
           if(k == portaSegreta.getRandomIndexDoor()) masso.containsDoor = true;
           double x = 55+40*arrayCoordinateMassi[k][0];
           double y = 205+40*arrayCoordinateMassi[k][1];
           masso.getImageViewMasso().relocate(x, y);
           Rectangle rettangoloMasso = new Rectangle(x,y,40,40);
           arrayRettangoliMasso.add(rettangoloMasso);
           arrayMassi.add(masso);
        }
        
        arrayMassi.stream().forEach((massoArray) -> {
            interfaccia.getContenitore().getChildren().add(massoArray.getImageViewMasso()); //03
        });
    }
    
    public void posizionaNemici(){ //04
        
        for(int h=0; h < quantiNemici; h++){
            
            Enemy enemy = new Enemy();
            enemy.ready = true;
            double x = 55+40*arrayCoordinateNemici[h][0];
            double y;
            if(arrayCoordinateNemici[h][1] == 10) y = 207+40*arrayCoordinateNemici[h][1]; //Aggiusto di qualche pixel lo spawn sull'ultima riga
            else y = 205+40*arrayCoordinateNemici[h][1];
            enemy.getEnemyImageView().relocate(x,y);
            arrayNemici.add(enemy);
        }
        arrayNemici.stream().forEach((nemicoArray) -> {
            interfaccia.getContenitore().getChildren().add(nemicoArray.getEnemyImageView()); //05
        });
    }
    
    public void resettaInterfaccia(){ //06
        
        arrayMassi.stream().forEach((massoArray) -> { //Svuoto il contenitore
            interfaccia.getContenitore().getChildren().remove(massoArray.getImageViewMasso());
        });
        arrayNemici.stream().forEach((nemicoArray) -> {
            interfaccia.getContenitore().getChildren().remove(nemicoArray.getEnemyImageView());
        });
        
        interfaccia.getContenitore().getChildren().remove(portaSegreta.getImageViewDoor());
        arrayRettangoliMasso.clear();
        arrayMassi.clear();
        arrayNemici.clear();
    }
    
    public ArrayList<Masso> getArrayMassi(){ return arrayMassi;}
    public ArrayList<Rectangle> getListMassi(){ return arrayRettangoliMasso;}
    public SecretDoor getSecretDoor() { return portaSegreta; }
    public ArrayList<Enemy> getArrayNemici(){ return arrayNemici;}
    public GestoreMessaggi getGestoreMessaggi() { return gestoreMessaggi; }
}

/*Note

01) A seconda della difficoltà cambia in numero di nemici e massi da inserire,
    questa funzione utilizza il GestoreCoordinateRandom per generare le coordinate
    di spawn dei nemici e dei massi
02) Tramite le coordinate ottenute da GestoreCoordinateRandom posiziono tot massi
    con i relativi rettangoli (facilitano le collisioni) 
03) Mostro i massi nella scena
04) Tramite le coordinate ottenute da GestoreCoordinateRandom posiziono tot nemici
05) Mostro i nemici nella scena
06) Svuoto l'array di rettangoli/nemici/massi e rimuovo tutti gli ImageView corrispondenti
    a massi e nemici dalla scena 
*/