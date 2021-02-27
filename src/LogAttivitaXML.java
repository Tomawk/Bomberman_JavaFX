 import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class LogAttivitaXML implements Serializable { // 00)
    private final String nomeEvento, indirizzoIpClient, dataOraCorrente; // 01)
    
    public LogAttivitaXML(String nomeEvento, String indirizzoIpClient) {
        this.nomeEvento = nomeEvento;
        this.indirizzoIpClient = indirizzoIpClient;
        dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()); // 02)
    }
    
    @Override
    public String toString() { // 03)
        XStream xs = new XStream();
        xs.useAttributeFor(LogAttivitaXML.class, "nomeEvento"); // 04)
        xs.useAttributeFor(LogAttivitaXML.class, "indirizzoIpClient"); // 04)
        xs.useAttributeFor(LogAttivitaXML.class, "dataOraCorrente"); // 04)
        return  "\n" + xs.toXML(this) ; // 05)
    }
}

/* Note:

00) Classe che rappresenta un log da inviare ad un server log
01) Il log è rappresentato dall'evento da loggare, l'indirizzo ip del client e il timestamp corrente
02) Genera il timestamp corrente
03) Metodo che converte un oggetto di tipo LogAttivitaXML in una stringa XML.
04) In base alle regole di buona progettazione XML, ogni membro della classe
    e' stato scelto come attributo in quanto si tratta di una stringa semplice
    (seconda regola).
05) \n inserito per rendere più leggibile il file di log del server

*/