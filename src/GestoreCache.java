import java.io.*;

public class GestoreCache { // 00)
    public final static void salva(InterfacciaStatica interfacciaS, String file) { // 01)
        try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) { 
            oout.writeObject(new Cache(interfacciaS)); 
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public final static Cache carica(String file) { //02
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) { 
            return (Cache)oin.readObject(); 
        } catch(IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null; 
    }
}

/* Note:

00) Gestisce la lettura e la scrittura su file binario della cache 
01) Salva la difficoltà selezionata e il campo di testo (Nickname) al momento della chiusura dell'app
    nel file passato come parametro, preleva le informazioni da salvare da InterfacciaStatica
02) Carica dal file binario, la cache degli input relativa ad una esecuzione
    precedente. Metodo chiamato all'avvio dell'applicazione
*/