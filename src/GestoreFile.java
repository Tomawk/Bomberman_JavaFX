
import java.io.*;
import java.nio.file.*;


public class GestoreFile { //00
    
    private final static String PERCORSO_RELATIVO = "..\\..\\"; // 01)
    
    public static void salva(Object o, String file) { // 02)
        try {
            Files.write( // 03)
                Paths.get(PERCORSO_RELATIVO + file), 
                o.toString().getBytes(),
                StandardOpenOption.APPEND
            );
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
     public static String carica(String file) { //04
        try {
            return new String(Files.readAllBytes(Paths.get(file)));  
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null; 
    }
     
}

/* Note:

00) Classe che si occupa di scrivere e leggere dati da file.
01) i file in cui e' necessario scrivere (XML e binari) si trovano nella cartella root 
    dell'applicazione.
02) Permette di salvare un Object o nel file indicato dalla String file
03) Scrittura
04) Carica il contenuto di un file e lo restituisce come String.
    Viene invocato per leggere dati dal file di configurazione XML
*/