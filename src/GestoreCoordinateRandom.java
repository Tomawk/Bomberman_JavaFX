
import java.util.*;

public class GestoreCoordinateRandom {
    
    private final int posizioniMassi[][], posizioniNemici[][]; //01
    private final int quanteRocce, quantiNemici;
    private int i, j;
    
    public GestoreCoordinateRandom(int numMassi, int numNemici){ //02
        
        
        
        quanteRocce = numMassi;
        posizioniMassi = new int[quanteRocce][2];
        
        quantiNemici = numNemici;
        posizioniNemici = new int[quantiNemici][2];
        
        for(int k=0; k<quanteRocce; k++){
            boolean controllo = false;
            while(controllo == false){ //03
                 i = RandomPositionX(i);
                 j = RandomPositionY(j);
                controllo = controllaOccupata(i,j); //04 
            }
            posizioniMassi[k][0]=i;
            posizioniMassi[k][1]=j;
            
        }
        
        
        for(int h=0; h<quantiNemici; h++){
            boolean controllo = false;
            while(controllo == false){
                i = RandomPositionX(i);
                j = RandomPositionY(j);
                controllo =controllaOccupata(i,j);
            }
            posizioniNemici[h][0]=i;
            posizioniNemici[h][1]=j;
        }
        
    }
    
    public int RandomPositionX(int randomX){ //05
        
        Random randX = new Random();
        randomX = randX.nextInt(13);
        return randomX;
    }
    
    public int RandomPositionY(int randomY){ //06
        Random randY = new Random();
        randomY = randY.nextInt(11);
        return randomY;
    }
    
    public boolean controllaOccupata(int x, int y){ //07
        if(x == 0 && y==0) return false; //08
        if(x == 0 && y==1) return false; //08
        if(x == 1 && y==0) return false; //08
        for(int k=0; k<posizioniMassi.length; k++){ //09
            if(x == posizioniMassi[k][0] && y == posizioniMassi[k][1]) return false;
        }
        
        for(int h=0; h<posizioniNemici.length; h++){ //10
            if(x == posizioniNemici[h][0] && y == posizioniNemici[h][1]) return false;
        }
        
        if(x%2 != 0 && y%2 != 0) return false; //11
        return true;
    }
    
    public int[][] getArrayMassi(){ return posizioniMassi;  }
    public int[][] getArrayNemici(){ return posizioniNemici; }
    
}

/* Note:

01) Array multidimensionale per memorizzare le coordinate di spawn dei massi e dei nemici,
    costituisce una matrice di 2 righe ( x = colonna e y = riga ) e n colonne dove n è il numero di massi/nemici
02) Costruttore, dati un numero di massi e nemici come argomento genera i due array multidimensionali con le coordinate
03) finchè la variabile di controllo è a false continuo a generare coordinate x e y
04) controllaOccupata() restituisce un booleano false se la coordinata generata non è idonea e true altrimenti
05) Genero una coordinata x randomica tra 0 e 13 dove 13 è il numero delle colonne della GridPane di InterfacciaStatica
06) Genero una coordinata y randomica tra 0 e 11 dove 11 è il numero di righe della Gridpane di InterfacciaStatica
07) Controllo se la x e la y sono idonee allo spawn
08) Escludo quelle colonne in quanto troppo vicine all'area di spawn
09) Controllo se ho già una coordinata con quella x e y tra i massi già posizionati
10) Controllo se ho già una coordinata con quella x e y tra i nemici già posizionati
11) Controllo la casella (x,y) non sia occupata da una roccia

*/