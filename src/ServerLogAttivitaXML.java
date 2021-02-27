import java.io.*;
import java.net.*;

public class ServerLogAttivitaXML { // 00)
    private final static int PORTA = 6789; // 01)
    private final static String FILE_LOG = "log.xml", // 02)
                                SCHEMA_LOG = "log.xsd"; // 03)
    
    public static void main(String[] args) {
        System.out.println("Server Log avviato.\n");
        
        try(ServerSocket serverSocket = new ServerSocket(PORTA)) { 
            while(true) { // 04
                try(Socket socket = serverSocket.accept(); 
                    ObjectInputStream oin = new ObjectInputStream(socket.getInputStream()); 
                ) {
                    String log = (String)oin.readObject(); 
                    System.out.println(log); 
                    if(ValidatoreXML.valida(log, SCHEMA_LOG, false)) 
                        GestoreFile.salva(log, FILE_LOG);
                }
            }
        } catch(IOException | ClassNotFoundException e) {
          System.err.println(e.getMessage());
        }
    }
}

/* Note:

00) Server che riceve ciclicamente log XML, e se validati correttamente, li
    aggiunge ad un file di log.
01) Porta server log
02) File in cui il server salva i log
03) Schema per la validazione del log
04) Il server è ciclico
*/