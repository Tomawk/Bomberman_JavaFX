import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;


public class GestoreMessaggi {
    
    
    private final Rectangle box; //01
    private final Label messaggio; //02
    private final StackPane stack; //03
    
    
    public GestoreMessaggi(){
        
        box = new Rectangle(470,230);
        messaggio = new Label();
        messaggio.setStyle("-fx-text-fill: white; -fx-font-size: 12;");
        
        box.setStroke(Color.WHITE);
        box.setFill(Color.rgb(105, 105, 105, 0.8));
        
        stack = new StackPane();
        stack.setLayoutX(80);
        stack.setLayoutY(310);
    }
    
    public void DeathMessage(String punteggio, int tempo, InterfacciaStatica interfacciaStatica){ //04
        messaggio.setText("GAME OVER :( \n \n" + "Ecco i tuoi risultati: \n\nPunteggio: " + punteggio + " \n\n" + "Tempo Rimasto: " + tempo + "s");
        stack.getChildren().addAll(box,messaggio );
        interfacciaStatica.getContenitore().getChildren().add(stack);
    }
    
    public void SelectionMessage(InterfacciaStatica interfacciaStatica){ //04
        messaggio.setText("ATTENZIONE \n \n" + "Selezionare una delle difficoltà \n \n"+ "dal menù a tendina in alto a destra \n \n"+ "FACILE | INTERMEDIO | DIFFICILE");
        stack.getChildren().addAll(box,messaggio);
        interfacciaStatica.getContenitore().getChildren().add(stack);
    }
    
    public void CongratsMessage(String punteggio, int tempo, InterfacciaStatica interfacciaStatica){ //04
        messaggio.setText("CONGRATULAZIONI :) \n \n" + "Ecco i tuoi risultati: \n\nPunteggio: " + punteggio + " \n\n" + "Tempo Rimasto: " + tempo + "s");
        stack.getChildren().addAll(box,messaggio);
        interfacciaStatica.getContenitore().getChildren().add(stack);
    }
    
    
    public void SvuotaBox(){ //05
        if(stack.getChildren().contains(box)) stack.getChildren().remove(box);
        stack.getChildren().remove(messaggio);
    }
    
    public StackPane getStackPane(){ return stack; } 
    
}

/* Note:

01) Rettangolo che fa da sfondo ai messaggi da mostrare a video
02) Label per il messaggio da mostrare a video
03) Stackpane per raccogliere il rettangolo e il label
04) Funzioni che settano il label del messaggio (GameOver|Vittoria|Selezione Difficolta)
    e lo aggiungono prima allo StackPane e poi al contenitore di InterfacciaStatica
05) Funzione per svuotare lo StackPane
*/