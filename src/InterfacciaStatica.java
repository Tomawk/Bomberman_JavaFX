
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

class InterfacciaStatica {
    private final AnchorPane contenitore; //01
    private final GridPane griglia;
    private final ArrayList<Rectangle> rettangoliRoccia; //02
    private final Button start;
    private final ComboBox difficolta; //03
    private final Label score,timer,titolo,livello,leaderboardLabel,nickname;
    private final Leaderboard leaderboard; //04
    private final TextField inputText;
    private final VBox contenitoreLeaderboard;
    
    public InterfacciaStatica(FileConfigurazioneXML config, String stringCache) {
         Cache cache = GestoreCache.carica(stringCache); //05
         contenitore = new AnchorPane();
         start = new Button("START");
         titolo = new Label("BOMBERMAN");
         griglia = new GridPane();
         inizializzaGriglia(griglia, config);
         score = new Label ("00000000");
         timer = new Label ("T: 000s");
         livello = new Label ("lvl:01");
         nickname = new Label("Nickname");
         inputText = new TextField();
         leaderboard = new Leaderboard();
         leaderboard.aggiornaListaPlayer(DatabaseScorePlayer.caricaPlayers(config)); //06
         leaderboardLabel = new Label("Leaderboard");
         contenitoreLeaderboard = new VBox();
         contenitoreLeaderboard.getChildren().addAll(leaderboardLabel,leaderboard);
         rettangoliRoccia  = new ArrayList<>();
         difficolta = new ComboBox();
         difficolta.getItems().addAll("FACILE","INTERMEDIO","DIFFICILE");
         if(cache == null){
             inputText.setText("Bomberman1");
             difficolta.setPromptText("DIFFICOLTA");
         } else {
             inputText.setText(cache.getNickname());
             if(cache.getIndexComboBox() == -1) difficolta.setPromptText("DIFFICOLTA");
             else difficolta.getSelectionModel().select(cache.getIndexComboBox());
         }
         impostaSfondoErba();
         impostaRocce(config);
         contenitore.getChildren().addAll(  titolo,griglia,start,difficolta,
                                            score, timer,livello,contenitoreLeaderboard,
                                            inputText,nickname
         );
    }
    
    
    public void impostaStileInterfaccia(FileConfigurazioneXML config) {
        contenitore.setPrefSize(900, 700);
        griglia.setStyle("-fx-background-color:rgba(0, 0, 0, 0); -fx-grid-lines-visible: true;  -fx-border-color: white; -fx-border-width: 5;");
        griglia.setLayoutX(50); griglia.setLayoutY(200);
        contenitore.setStyle("-fx-background-color: " + config.coloreSfondo +";");
        titolo.getStyleClass().add("outline");
        titolo.setId("titolo");
        titolo.setLayoutX(110); titolo.setLayoutY(60);
        start.getStyleClass().add("outline");
        start.setLayoutX(680); start.setLayoutY(40);
        difficolta.getStyleClass().add("outline");
        difficolta.setLayoutX(650); difficolta.setLayoutY(100);
        score.setStyle("-fx-text-fill: white; -fx-font-size: " + config.dimensioneFont + "px;");
        score.setLayoutX(80); score.setLayoutY(165);
        timer.setStyle("-fx-text-fill: white; -fx-font-size: " + config.dimensioneFont + "px;");
        timer.setLayoutX(270); timer.setLayoutY(165);
        livello.setStyle("-fx-text-fill: white; -fx-font-size: " + config.dimensioneFont + "px;");
        livello.setLayoutX(440); livello.setLayoutY(165);
        impostaStileLeaderboard();
        inputText.setStyle("-fx-text-fill: blue; -fx-font-size: 14px; -fx-background-color: yellow; -fx-alignment:center; -fx-font-weight: bold;");
        inputText.setLayoutX(670); inputText.setLayoutY(180);
        nickname.setStyle("-fx-text-fill: blue;  -fx-font-size: 14px;");
        nickname.setLayoutX(710); nickname.setLayoutY(160);
    }
    
    public void impostaStileLeaderboard(){
        contenitoreLeaderboard.setLayoutX(625);
        contenitoreLeaderboard.setLayoutY(230);
        leaderboardLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 20px");
    }
    
    public void inizializzaGriglia(GridPane grid, FileConfigurazioneXML config){
        for(int i = 0; i < config.dimensioniGriglia[1]; i++) {
            ColumnConstraints column = new ColumnConstraints(40);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < config.dimensioniGriglia[0]; i++) {
            RowConstraints row = new RowConstraints(40);
            grid.getRowConstraints().add(row);
        }
    }
    
    public void impostaSfondoErba(){
        ImageView erbaImageView = new ImageView("file:../../AddOn/Immagini/grass5.jpg");
        erbaImageView.setFitHeight(440);
        erbaImageView.setFitWidth(520);
        erbaImageView.setLayoutX(55); erbaImageView.setLayoutY(205);
        contenitore.getChildren().add(erbaImageView);
    }
    
    public void impostaRocce(FileConfigurazioneXML config){ //07
        for(int i=0; i<config.dimensioniGriglia[1]; i++){
            if(i%2 != 0){
                for(int j=0; j<config.dimensioniGriglia[0] ; j++){
                    if( j%2 != 0) { 
                        double x = 55+40*i;
                        double y = 205+40*j;
                        Roccia roccia = new Roccia();
                        Rectangle rettangoloRoccia = new Rectangle(x,y,40,40);
                        rettangoloRoccia.setFill(Color.TRANSPARENT);
                        rettangoliRoccia.add(rettangoloRoccia);
                        griglia.add(roccia.getImageViewRoccia(), i, j);
                    }            
		}
            }
	}
    }
    
    public void preparaInterfaccia(Character character, GestoreMessaggi gestoreMessaggi) { //08
        score.setText("00000000");
        contenitore.getChildren().remove(character.getTombstone());
        gestoreMessaggi.SvuotaBox();
        contenitore.getChildren().remove(gestoreMessaggi.getStackPane());
    }
   
    public AnchorPane getContenitore() { return contenitore; }
    public Label getTimerLabel() { return timer; }
    public Label getScoreLabel() { return score; }
    public Button getStartButton() { return start; }
    public ArrayList<Rectangle> getListRoccia(){ return rettangoliRoccia;}
    public ComboBox getComboBox() { return difficolta; }
    public String getInputText() { return inputText.getText();}
    public TextField getTextField() { return inputText; }
    public Leaderboard getLeaderboard() { return leaderboard;}
    
}

/*Note

01) Contenitore di tutti gli elementi dell'interfaccia
02) ArrayList di rettangoli per ogni rettangolo invisibile sotto ciascuna roccia (facilita la gestione delle collisioni)
03) Menu di selezione per la difficoltà
04) Estensione di TableView
05) Carico informazioni dalla cache
06) Mostro (se presenti) eventuali score precedenti nella TableView
07) Imposta le rocce fisse nella mappa di gioco, vengono posizionate con i relativi rettangoli
    nelle caselle di riga e colonna pari
08) Funzione da chiamare appena viene premuto Start, resetta l'interfaccia in caso di partite precendenti
*/