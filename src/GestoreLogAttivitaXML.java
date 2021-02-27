import java.io.*;
import java.net.*;

public class GestoreLogAttivitaXML {  //00
    private static void invia(LogAttivitaXML log, String ipServerLog, int portaServerLog) {  //01
        try(Socket socket = new Socket(ipServerLog, portaServerLog); 
            ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
        ) {
            oout.writeObject(log.toString());
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void creaLog(String evento, FileConfigurazioneXML config) { 
        invia(
            new LogAttivitaXML(evento, config.ipClient), 
            config.ipServerLog, 
            config.portaServerLog
        );
    }
}

/* Note:

00) Classe che gestisce i log XML da inviare ad un server log, vengono loggati:
    - Avvio dell’applicazione (“Apertura Applicazione”)
    - Pressione del pulsante START (“Click Botone Start” )
    - Una bomba è stata posizionata (“Bomba Piazzata”)
    - Un nemico è stato ucciso (“Nemico Ucciso”)
    - Una porta segreta è stata rivelata (“Porta Segreta Trovata!”)
    - Termine dell’applicazione (“Chiusura Applicazione”)
01) Funzione di invio del log al server di log, ip e porta sono specificati come parametri del metodo
02) Funzione di utility che crea un oggetto LogAttivitaXML e lo invia
    tramite il metodo invia().

*/