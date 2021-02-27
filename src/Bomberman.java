import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.util.*;

public class Bomberman extends Application {

     private InterfacciaStatica interfacciaStatica;
     private InterfacciaDinamica interfacciaDinamica;
     
     private final static String FILE_CONFIGURAZIONE = "config.xml", //01
                                 SCHEMA_CONFIGURAZIONE = "config.xsd", //02
                                 FILE_CACHE = "cache.bin"; //03
     
     private int tempoRimasto;
     private Character character; 
     private AnimationTimer animationTimer;
     private Timeline timelineTimer, timelineMovement;
     private Score scoreFinale;
     private String stringaDifficolta;
     private GestoreCollisioni gestoreCollisioni;
     private FileConfigurazioneXML config;
     private GestoreSuoni gestoreSuoni;
     
     
     public void start(Stage stage) {
        inizializzaOggetti();
        settaTimeline();
        GestoreLogAttivitaXML.creaLog("Apertura applicazione", config);
        Group root = new Group( interfacciaStatica.getContenitore()); //04
        stage.setTitle("Bomberman"); 
        Scene scene = new Scene(root,900, 700); 
        File f = new File("AddOn/CSS/outline.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/")); //05
        gestoreSuoni.avviaMusicaTitle();
        scene.setOnKeyPressed( event -> movimentoPersonaggioTrue(event) ); 
        scene.setOnKeyReleased( event -> movimentoPersonaggioFalse(event));
        animationTimer = new AnimationTimer() {  //06
            public void handle(long now) {
                chiamaAdOgniTick();
            }
        };
        interfacciaStatica.getStartButton().setOnAction(event -> inizioGame(event));
        stage.setScene(scene);
        stage.setResizable(false); //07
        stage.show();
        stage.getIcons().add(new Image("file:../../AddOn/Immagini/icon.png")); //08
        
        stage.setOnCloseRequest((WindowEvent we) -> {   
            GestoreLogAttivitaXML.creaLog("Chiusura applicazione", config);  //09
            GestoreCache.salva(interfacciaStatica, FILE_CACHE); //10
        });
    }
     
    public void inizializzaOggetti(){
        gestoreSuoni = new GestoreSuoni();
        config = new FileConfigurazioneXML( ValidatoreXML.valida(FILE_CONFIGURAZIONE, SCHEMA_CONFIGURAZIONE, true) ? 
                    GestoreFile.carica(FILE_CONFIGURAZIONE) : null 
        ); //11
        interfacciaStatica = new InterfacciaStatica(config,FILE_CACHE); 
        interfacciaStatica.impostaStileInterfaccia(config); 
        interfacciaDinamica = new InterfacciaDinamica(interfacciaStatica);
        character = new Character(interfacciaStatica,gestoreSuoni); 
        gestoreCollisioni = new GestoreCollisioni(interfacciaStatica,interfacciaDinamica,gestoreSuoni);
    }
     
    public void settaTimeline(){ //12
        timelineTimer = new Timeline(); 
        timelineTimer.setCycleCount(Timeline.INDEFINITE);
        timelineTimer.getKeyFrames().add(
            new KeyFrame( //13
                Duration.seconds(1), 
                event -> oneSecondBack() 
            )
        );
        timelineMovement = new Timeline(); 
        timelineMovement.setCycleCount(Timeline.INDEFINITE);
        timelineMovement.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(20), 
                event -> enemyMovement() //14
            )
        );
    }
     
    public void chiamaAdOgniTick(){
        int dx = 0, dy = 0, dx1 = 0, dy1=0;
        character.impostaAnimazioneDirezione(); //15
        if (character.goNorth) { dy -= 3; dy1 += 3;} //16
        if (character.goSouth) { dy += 3; dy1 -= 3;} 
        if (character.goEast)  { dx += 3; dx1 -= 3;}
        if (character.goWest)  { dx -= 3; dx1 += 3; }
        if (character.bombPlaced) {  //17
            character.displayBomb(config); 
        }
        character.moveHeroBy(dx, dy); //18
        gestoreCollisioni.gestisciCollisioneRocce(character,dx1,dy1); //19
        gestoreCollisioni.gestisciCollisioneMassi(character,dx1, dy1); //20
        if(character.getBomb().isExploded == true){
            gestoreCollisioni.gestisciCollisioneBombaNemici(character,scoreFinale,config); //21
            gestoreCollisioni.gestisciCollisioniBombaMassi(character,scoreFinale,config);  //22
            if(gestoreCollisioni.gestisciCollisioneBombaCharacter(character,interfacciaDinamica.getGestoreMessaggi(), tempoRimasto) == true) reset(); //23
        }
        if (interfacciaDinamica.getSecretDoor().isFound == true) {
            if(gestoreCollisioni.gestisciCollisioneCharacterDoor(character,scoreFinale, interfacciaDinamica.getGestoreMessaggi(), tempoRimasto) == true) {
                DatabaseScorePlayer.inserisciNuovoScore(config, interfacciaStatica.getInputText(), scoreFinale.getScoreNumber(), stringaDifficolta);
                interfacciaStatica.getLeaderboard().aggiornaListaPlayer(DatabaseScorePlayer.caricaPlayers(config));
                reset();
            } //24
        }
        if(gestoreCollisioni.gestisciCollisioneNemiciBomberman(character,interfacciaDinamica.getGestoreMessaggi(),tempoRimasto) == true) reset(); //25
    }
    
    public void inizioGame(ActionEvent event){ //26
        
        GestoreLogAttivitaXML.creaLog("Click Bottone Start", config);
        gestoreSuoni.avviaMusicaGioco();
        interfacciaStatica.preparaInterfaccia(character,interfacciaDinamica.getGestoreMessaggi());
        if(interfacciaStatica.getComboBox().getSelectionModel().isEmpty() == true) { //27
            interfacciaDinamica.getGestoreMessaggi().SelectionMessage(interfacciaStatica);
        } else {
            if(interfacciaStatica.getComboBox().getValue() == "FACILE") { tempoRimasto = 150; scoreFinale = new Score("FACILE"); stringaDifficolta = "FACILE";} // Tempo rimasto iniziale
            if(interfacciaStatica.getComboBox().getValue() == "INTERMEDIO") { tempoRimasto = 120; scoreFinale = new Score("INTERMEDIO"); stringaDifficolta = "INTERMEDIO";}
            if(interfacciaStatica.getComboBox().getValue() == "DIFFICILE") {tempoRimasto = 90; scoreFinale = new Score("DIFFICILE"); stringaDifficolta = "DIFFICILE"; }
            interfacciaStatica.getTimerLabel().setText("T: " + tempoRimasto + "s"); 
            interfacciaDinamica.impostaCoordinate(stringaDifficolta); //28
            interfacciaDinamica.posizionaMassi(); //29
            interfacciaDinamica.posizionaNemici(); //30
            character.Spawn(); //31 
            animationTimer.start(); 
            timelineTimer.play(); 
            timelineMovement.play(); //32
            interfacciaStatica.getStartButton().setDisable(true); 
            interfacciaStatica.getTextField().setDisable(true); //33
        }     
    }
    
    public void oneSecondBack(){
        tempoRimasto --; 
        interfacciaStatica.getTimerLabel().setText("T: " + tempoRimasto + "s"); //34
        scoreFinale.secondiRimasti = tempoRimasto;
        if(tempoRimasto == 9){ //35
            gestoreSuoni.avviaSuonoTimer();
        }
        if(tempoRimasto == 0){ //36
            character.ready = false;
            interfacciaDinamica.getGestoreMessaggi().DeathMessage(scoreFinale.aggiornaLabelScore(),tempoRimasto,interfacciaStatica);
            gestoreSuoni.avviaMusicaGameOver();
            reset();
        }
    }
    
    public void movimentoPersonaggioTrue(KeyEvent event) { //37
                switch (event.getCode()) { 
                    case W:  character.goNorth = true; break; 
                    case S:  character.goSouth = true; break; 
                    case A:  character.goWest  = true; break;
                    case D:  character.goEast  = true; break;
                    case SHIFT: character.bombPlaced = true; break;
                }
    }
    
    public void movimentoPersonaggioFalse(KeyEvent event) { //38
                switch (event.getCode()) {
                    case W:  character.goNorth = false; break; 
                    case S:  character.goSouth = false; break; 
                    case A:  character.goWest  = false; break;
                    case D:  character.goEast  = false; break;
                    case SHIFT: character.bombPlaced = false; break;
                }
    }
    
    public void enemyMovement(){ //39
        for(int i=0; i<interfacciaDinamica.getArrayNemici().size(); i++){
            boolean moved = false; //40
            int dx=0,dy=0,dx1=0,dy1=0;
            int ritorno = getRandomDirection(i,interfacciaDinamica.getArrayNemici().get(i).previousBoolean); 
            if(interfacciaDinamica.getArrayNemici().get(i).goNorth == true) { dy -= 3; dy1 += 3;} 
            if(interfacciaDinamica.getArrayNemici().get(i).goSouth == true) { dy += 3; dy1 -= 3;}
            if(interfacciaDinamica.getArrayNemici().get(i).goEast == true)  { dx += 3; dx1 -= 3;}
            if(interfacciaDinamica.getArrayNemici().get(i).goWest == true)  { dx -= 3; dx1 += 3; }
            interfacciaDinamica.getArrayNemici().get(i).moveEnemyBy(dx,dy); 
            boolean collisioneBordi = interfacciaDinamica.getArrayNemici().get(i).faCollisioneMuri(); //41
            boolean r1 = gestoreCollisioni.gestisciCollisioneNemiciRocce(interfacciaDinamica.getArrayNemici().get(i),dx1,dy1); //42
            boolean r2 = gestoreCollisioni.gestisciCollisioneNemiciMassi(interfacciaDinamica.getArrayNemici().get(i),dx1,dy1); //43
            boolean r3 = gestoreCollisioni.gestisciCollisioneNemiciDoor(interfacciaDinamica.getArrayNemici().get(i),dx1,dy1); // 44
            if(r1 == true && r2 == true && collisioneBordi == false && r3 == true) { moved=true; 
            interfacciaDinamica.getArrayNemici().get(i).contatoreMovimento = 0; } //45
            else interfacciaDinamica.getArrayNemici().get(i).contatoreMovimento += 1; //46
            if(moved == true) { 
                interfacciaDinamica.getArrayNemici().get(i).previousBoolean = ritorno;
                if(ritorno == 0) interfacciaDinamica.getArrayNemici().get(i).booleanOpposto = 1;
                if(ritorno == 1) interfacciaDinamica.getArrayNemici().get(i).booleanOpposto = 0;
                if(ritorno == 2) interfacciaDinamica.getArrayNemici().get(i).booleanOpposto = 3;
                if(ritorno == 3) interfacciaDinamica.getArrayNemici().get(i).booleanOpposto = 2;
            } 
            if(interfacciaDinamica.getArrayNemici().get(i).contatoreMovimento == 30) { //47
                int appoggio = interfacciaDinamica.getArrayNemici().get(i).previousBoolean;
                interfacciaDinamica.getArrayNemici().get(i).previousBoolean = interfacciaDinamica.getArrayNemici().get(i).booleanOpposto;
                interfacciaDinamica.getArrayNemici().get(i).booleanOpposto = appoggio;
                interfacciaDinamica.getArrayNemici().get(i).contatoreMovimento = 0;
            }
                
        }
    }
    
    public void reset(){ //48
        animationTimer.stop();
        timelineTimer.stop();
        timelineMovement.stop();
        gestoreSuoni.interrompiSuoni();
        interfacciaStatica.getStartButton().setDisable(false);
        interfacciaStatica.getTextField().setDisable(false); 
        interfacciaDinamica.resettaInterfaccia(); 
    }
    
  
    public int getRandomDirection(int i, int previousBoolean1){ //49
        interfacciaDinamica.getArrayNemici().get(i).goNorth = false;
        interfacciaDinamica.getArrayNemici().get(i).goSouth = false;
        interfacciaDinamica.getArrayNemici().get(i).goEast = false;
        interfacciaDinamica.getArrayNemici().get(i).goWest = false;
        int oppositeBoolean = -1;
        int newBoolean;
        Random randomIndex = new Random();
        newBoolean = randomIndex.nextInt(4);
        if(previousBoolean1 != -1){ //50
            if(previousBoolean1 == 0) oppositeBoolean = 1;
            if(previousBoolean1 == 1) oppositeBoolean = 0;
            if(previousBoolean1 == 2) oppositeBoolean = 3;
            if(previousBoolean1 == 3) oppositeBoolean = 2;
            while(newBoolean == oppositeBoolean){ 
            Random randomIndex1 = new Random();
            newBoolean = randomIndex1.nextInt(4);
            }
        }
        if(newBoolean == 0) { interfacciaDinamica.getArrayNemici().get(i).goNorth = true; }
        if(newBoolean == 1) { interfacciaDinamica.getArrayNemici().get(i).goSouth = true; }
        if(newBoolean == 2) { interfacciaDinamica.getArrayNemici().get(i).goEast = true;  }
        if(newBoolean == 3) { interfacciaDinamica.getArrayNemici().get(i).goWest = true;  }
        previousBoolean1 = newBoolean;
        return previousBoolean1; 
        } 
}


/* Note
01) All'avvio l'applicazione legge da file di configurazione le seguenti informazioni
    - username, password, porta Dbms
    - numero di righe da visualizzare nella tabella Leaderboard
    - font, dimensioni, colore del background
    - dimensioni (righe e colonne) della griglia di gioco
    - l’indirizzo IP del client, l’indirizzo IP e la porta del server log
02) Schema per la validazione del file di configurazione
03) Alla chiusura dell'applicazione viene salvato su file binario
    - la difficolta attualmente selezionata
    - il nickname attualmente inserito nel campo di testo con label Nickname
04) Gruppo da mostrare nella scena
05) Aggiunto un foglio di stile alla scena per lo impostare lo stile di alcuni elementi dell'interfaccia
06) Dichiarazione AnimationTimer
07) Istruzione per impedire il resize della finestra
08) Impostata icona applicazione
09) Viene creato un log con evento "Chiusura Applicazione" che viene inviato al server di log
10) Viene salvato in formato XML il log su log.xml
11) Se la validazione del file di configurazione va a buon fine, viene caricato il contenuto
12) Metodo per inizializzare la Timeline per il timer e la Timeline per il movimento dei nemici
13) Ogni secondo chiama la funzione oneSecondBack() che decrementa il timer
14) Ogni 20 millisecondi chiama la funzione enemyMovement() che fa muovere randomicamente i nemici
15) Animazione per il movimento di Bomberman
16) A seconda del tasto premuto vengono impostati a true alcuni booleani di character.
    In base al booleano viene incrementato/decrementato dx (spostamento verso destra/sinistra) 
    o dy(spostamento verso l'alto/basso). dx1 e dy1 rappresentano il movimento opposto a dx e dy.
17) Se bombPlaced (di Character) viene impostato a true significa che la bomba è stata piazzata, viene chiamata displayBomb()
18) Sposta le ImageView di Character in base ai valori di dx e dy
19) Gestisce le collisioni tra Bomberman e le rocce, impedisce di muoversi sulle rocce
20) Gestisce le collisioni tra Bomberman e i massi, impedisce di muoversi sui massi
21) Se la bomba è esplosa, controlla se è stata fatta una collisione tra un nemico e l'esplosione
22) Se la bomba è esplosa, controlla se è stata fatta una collisione tra un masso e l'esplosione
23) Se la bomba è esplosa e c'è stata una collisione tra Bomberman e l'esplosione, GameOver e viene chiamata la reset()
24) Se c'è una collisione tra Bomberman e la botola (ci va sopra), il gioco termina
    con una vittoria, viene inserito Nickname|Score|Difficoltà nel Database,
    viene aggiornata la TableView Leaderboard e viene chiamata la reset()
25) Se c'è una collisione tra un nemico e Bomberman, GameOver e viene chiamata la reset()
26) Funzione eseguita quando viene cliccato il bottone START
27) In caso di difficoltà non inserita, viene mostrato un messaggio che ce lo comunica
28) In base alla difficoltà vengono impostate tramite la classe GestoreCoordinateRandom
    chiamata da InterfacciaDinamica le coordinate di spawn per i massi e i nemici
29) Posiziono in base alle coordinate precedentemente ottenute i massi
30) Posiziono in base alle coordinate precedentemente ottenute i nemici
31) Posiziono e mostro l'ImageView di Bomberman nella casella di Spawn (in alto a sx)
32) Avvio l'AnimationTimer e le Timeline(Movimento nemici e timer)
33) Disattivo il bottone START e il TextField mentre la partita è in corso
34) Aggiorno il label del timer ogni secondo
35) Quando mancano 9 secondi avvio il suono del timer che sta per terminare
36) Se il tempo termina, mostro un messaggio di GameOver e chiamo la reset()
37) Funzione chiamata dall'evento onKeyPressed, in base al tasto premuto imposta a true
    i booleani per il movimento
38) Funzione chiamata dall'evento onKeyReleased, in base al tasto rilasciato imposta a false
    i booleani per il movimento
39) Funzione per il movimento dei nemici, chiamata ogni 20ms dalla Timeline
40) Booleano per vedere effettivamente se il nemico si è mosso e non ha fatto collisioni
41) Controllo se il nemico ha fatto una collisione con un bordo
42) Controllo se ha fatto una collisione con una roccia
43) Controllo se ha fatto una collisione con un masso
44) Controllo se ha fatto una collisione con la botola
45) Se non ha fatto collisioni e si è mosso realmente, resetto il contatore dei movimenti
    e imposto a true il booleano moved. Il contatore dei movimenti serve per evitare il blocco
    dei nemici nella mappa, non avendo un movimento dove è concesso il tornare indietro da
    dove siamo venuti mi posso bloccare in alcune circostanze e perciò se il contatore
    supera una determinata soglia significa che si è bloccato e lo devo obbligare a tornare indietro
46) Altrimenti incremento il contatore dei movimenti 
47) Se si è bloccato lo obbligo a muoversi dalla parte opposta
48) Funzione reset del gioco, permette di iniziare un altro gioco
49) Funzione che genera un intero corrispondente ad una delle 4 possibili direzioni di movimento.
    Non permette di generare una direzione uguale a quella opposta alla precedente
50) Prima iterazione
*/