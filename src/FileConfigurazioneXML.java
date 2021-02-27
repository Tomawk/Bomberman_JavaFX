import com.thoughtworks.xstream.*;

public class FileConfigurazioneXML { //01

    private static final int RIGHE_DEFAULT_GRIGLIA = 11, //02
                             COLONNE_DEFAULT_GRIGLIA = 13, //02
                             NUMERO_RIGHE_TABLEVIEW_DEFAULT = 15, //02
                             PORTA_SERVER_LOG_DEFAULT = 6789, //02
                             DIMENSIONE_FONT_DEFAULT = 17, //02
                             PORTA_DEFAULT_DBMS = 3306; //02
    private static final String COLORE_DEFAULT_SFONDO = "blue", //02
                                USERNAME_DEFAULT_DBMS = "root", //02
                                PASSWORD_DEFAULT_DBMS = ""; //02
    
    
    public final int dimensioneFont; 
    public final int[] dimensioniGriglia; //03
    public final String coloreSfondo;
    public final int numeroRighe;  //04
    public final String ipClient; 
    public final String ipServerLog; 
    public final int portaServerLog; 
    public final String usernameDbms;
    public final String passwordDbms;
    public final int portaDbms;
    
    public FileConfigurazioneXML(String xml){
        dimensioniGriglia = new int[2];
        
        if(xml == null || xml.compareTo("") == 0) { //05
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioniGriglia[0] = RIGHE_DEFAULT_GRIGLIA;
            dimensioniGriglia[1] = COLONNE_DEFAULT_GRIGLIA;
            coloreSfondo = COLORE_DEFAULT_SFONDO;
            numeroRighe = NUMERO_RIGHE_TABLEVIEW_DEFAULT;
            ipClient = "127.0.0.1";
            ipServerLog = "127.0.0.1";
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            usernameDbms = USERNAME_DEFAULT_DBMS;
            passwordDbms = PASSWORD_DEFAULT_DBMS;
            portaDbms = PORTA_DEFAULT_DBMS;
           
        }
        else {
            FileConfigurazioneXML f = (FileConfigurazioneXML)creaXStream().fromXML(xml); //06
            dimensioneFont = f.dimensioneFont;
            dimensioniGriglia[0] = f.dimensioniGriglia[0];
            dimensioniGriglia[1] = f.dimensioniGriglia[1];
            coloreSfondo = f.coloreSfondo;
            numeroRighe = f.numeroRighe;
            ipClient = f.ipClient;
            ipServerLog = f.ipServerLog;
            portaServerLog = f.portaServerLog;
            usernameDbms = f.usernameDbms;
            passwordDbms = f.passwordDbms;
            portaDbms = f.portaDbms;
        }
    }
    
    public final XStream creaXStream() {
        XStream xs = new XStream();
        xs.useAttributeFor(FileConfigurazioneXML.class, "numeroRighe"); //07
        xs.useAttributeFor(FileConfigurazioneXML.class, "ipClient");  //07
        xs.useAttributeFor(FileConfigurazioneXML.class, "ipServerLog");  //07
        xs.useAttributeFor(FileConfigurazioneXML.class, "portaServerLog");  //07
        return xs;
    }
    
    public String toString() { //08
        return creaXStream().toXML(this); 
    }
   
}


/* Note:
01) Classe contenente i Parametri Di Configurazione, prelevati da un file XML.
    Esso contiene:
    - username, password, porta Dbms
    - numero di righe da visualizzare nella tabella Leaderboard
    - font, dimensioni, colore del background
    - dimensioni (righe e colonne) della griglia di gioco
    - l’indirizzo IP del client, l’indirizzo IP e la porta del server log
02) Valori di default nel caso in cui il file XML di configuazione dovesse
    mancare o che non venga validato.
03) Numero righe e colonne della GridPane di InterfacciaStatica
04) Numero righe da visualizzare della TableView
05) Nel caso in cui il file XML di configuazione dovesse mancare o venga
    validato, il costruttore usa dei valori di default.
06) Viene deserializzata la stringa XML dei Parametri Di Configurazione
    passata come argomento al costruttore.
07) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di un numero semplice (seconda regola).
08) Serializza l'oggetto in XML.
*/