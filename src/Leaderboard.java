
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

public class Leaderboard extends TableView<Player> { //01
    
    private final Label subtitle; 
    private final VBox contenitore; //02
    private final ObservableList<Player> listaOsservabilePlayer; //03
    
    public Leaderboard(){
        subtitle = new Label("Leaderboard");
        contenitore = new VBox();
        TableColumn nicknameCol = new TableColumn("Nickname");
        nicknameCol.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn difficoltaCol = new TableColumn("Difficolta");
        difficoltaCol.setCellValueFactory(new PropertyValueFactory<>("difficolta"));
        listaOsservabilePlayer = FXCollections.observableArrayList(); 
        setItems(listaOsservabilePlayer);
        getColumns().addAll(nicknameCol, scoreCol, difficoltaCol);
    } 
    
    public void aggiornaListaPlayer(List<Player> player) { //04
        listaOsservabilePlayer.clear();
        listaOsservabilePlayer.addAll(player);    
    }
   
    
}

/* Note:

01) Estende TableView e permette di mostrare i record del Database relativi ai vari punteggi
02) Contenitore per TableView e Label
03) ListaOsservabile contenente i punteggi da visualizzare
04) aggiorna la listaOsservabile svuotandola e inserendoci al suo posto gli elementi della lista passata come argomento


*/