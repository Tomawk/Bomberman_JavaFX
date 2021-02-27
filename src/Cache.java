import java.io.*;

public class Cache implements Serializable { 
    private final String nickname;
    private int indexComboBox;
    
    public Cache(InterfacciaStatica interfacciaStatica) { // 01)
       nickname = interfacciaStatica.getInputText();
       indexComboBox = -1;
       if(interfacciaStatica.getComboBox().getValue() == "FACILE") { indexComboBox = 0;} // Tempo rimasto iniziale
       if(interfacciaStatica.getComboBox().getValue() == "INTERMEDIO") { indexComboBox = 1;}
       if(interfacciaStatica.getComboBox().getValue() == "DIFFICILE") { indexComboBox = 2; }
    }
    
    public String getNickname(){ return nickname; }
    public int getIndexComboBox() { return indexComboBox; }
}
/* Note:
01) Il costruttore, invocato al momento del salvataggio della cache, e quindi
    alla chiusura dell'applicazione, salva la difficoltà selezionata e il contenuto
    del TextField (Nickname) prelevati da InterfacciaStatica
*/
