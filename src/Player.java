
import javafx.beans.property.*;

    
    public class Player { //01
        private final SimpleStringProperty nickname,difficolta,id;
        private final SimpleIntegerProperty score;
    
        public Player(String i, String n, int s, String d){
             id = new SimpleStringProperty(i);
             nickname = new SimpleStringProperty(n);
             score = new SimpleIntegerProperty(s);
             difficolta = new SimpleStringProperty(d);
        }
        
        public String getId() { return id.get(); }
        public String getNickname() { return nickname.get(); }
        public int getScore() { return score.get();}
        public String getDifficolta() { return difficolta.get();}
    
    }

/* Note:

01) Classe bean, la tabella nel database possiede 4 colonne id|nickname|score|difficolta,
    nella TableView l'id non viene mostrato
*/