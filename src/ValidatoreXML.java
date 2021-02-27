
import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML { //00
    
    private final static String PERCORSO_RELATIVO = "..\\..\\"; //01
    
    public static boolean valida(String xml, String fileSchema, boolean file) {  //02
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();  //03
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);  //04
            Document d; //05
            if(file) //06
                d = db.parse(new File(xml)); //07
            else  //08
                d = db.parse(new InputSource(new StringReader(xml))); //09
            Schema s = sf.newSchema(new StreamSource(new File(((fileSchema.compareTo("log.xsd") == 0) ? PERCORSO_RELATIVO : "") + fileSchema))); //10
            s.newValidator().validate(new DOMSource(d)); //11
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException) 
                System.err.println("Errore di validazione");
            
            System.err.println(e.getMessage());
            return false;  //12
        }
        return true; //13
    }
}

/* Note:

00) Classe per la validazione di file o stringhe XML.
    Ritorna un booleano il cui valore e' true se la validazione avviene con
    successo, e false altrimenti.
01) il metodo di ValidatoreXML e' chiamato dalla classe Bomberman e dalla
    classe ServerLogAttivitaXML: quest'ultima viene eseguita dalla directory
    \Bomberman\build\classes (dove \Bomberman\ e' la directory root
    dell'applicazione), mentre il file schema di log di cui ha bisogno si
    trova nella root dell'applicazione.
    Si antepone quindi, durante la ricerca del file schema di log,
    la stringa "..\..\", per specificare che il file in questione si trova
    nella directory padre della directory padre di \Bomberman\build\classes.
02) Valida un file o una stringa XML (primo parametro) secondo un file schema
    specificato da fileSchema. Il terzo parametro distingue il caso di
    validazione di un file XML o direttamente di una stringa XML, utile nel caso
    dei log.
03) Serve per ottenere documenti DOM da piu' tipi di sorgenti XML,
    come in questo caso da un file o da una stringa.
04) Funge da compilatore di schema XML: infatti per la sua creazione necessita l'URI 
    di un XML schema namespace per definire un linguaggio di schema.
05) Oggetto in grado di rappresentare un documento DOM (HTML o XML)
06) Se l'input XML è da prelevare in un file
07) Effettua il parse del file XML e restituisce un oggetto DOM. (API)
08) Se l'input XML è direttamente una stringa passata come primo argomento
09) Parse analogo in caso di String
10) Oggetto Schema creato dallo SchemaFactory, ottenuto prelevando il file
    di schema XSD. La classe ServerLogAttivitaXML e' l'unica mandata in
    esecuzione dalla directory \Bomberman\build\classes, percio' per
    raggiungere il file schema di log, necessita di anteporre al percorso del
    file, il percorso relativo.
11) Viene creato un oggetto validatore che valida il documento DOM derivante
    dal documento XML, sullo Schema appena creato.
12) In caso si verifichi qualsiasi eccezione il documento non è considerato validato e viene restituito false
13) Altrimenti viene restituito true
*/