
import java.sql.*;
import java.util.*;

public class DatabaseScorePlayer {

    public static List<Player> caricaPlayers(FileConfigurazioneXML config) { //01
        List<Player> listaPlayerRidotta = new ArrayList<>();
        int counter = 0;
        try ( 
            Connection co = DriverManager.getConnection("jdbc:mysql://localhost:" + config.portaDbms + "/bomberman", 
                                                                                           config.usernameDbms , 
                                                                                           config.passwordDbms
            );
            Statement st = co.createStatement(); 
        ) {
            ResultSet rs = st.executeQuery("SELECT * FROM leaderboard ORDER BY score DESC"); //02
            while (rs.next()) { 
                if(counter < config.numeroRighe) //03
                   listaPlayerRidotta.add(new Player(rs.getString("id"),rs.getString("nickname"), rs.getInt("score"), rs.getString("difficolta")));
                else break;
                counter++;
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return listaPlayerRidotta; //04
    }
    
    public static void inserisciNuovoScore(FileConfigurazioneXML config, String nickname, int score, String difficolta){ //05
        try ( 
            Connection co = DriverManager.getConnection("jdbc:mysql://localhost:" + config.portaDbms + "/bomberman", 
                                                                                           config.usernameDbms, 
                                                                                           config.passwordDbms
            );
            PreparedStatement ps = co.prepareStatement("INSERT INTO leaderboard VALUES (?,?,?,?)");
        ) {
            String uniqueID = UUID.randomUUID().toString();
            ps.setString(1, uniqueID);
            ps.setString(2, nickname);
            ps.setInt(3, score);
            ps.setString(4, difficolta);
            ps.executeUpdate(); 
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }
}

/* Note:

01) Restituisce una List<Player> formata da tot record prelevati dal database
02) Query di selezione, record ordinati in ordine decrescente per punteggio
03) Scorrendo il resultset, prelevo i primi n record (n deciso da file di configurazione) e gli inserisco in una List<Player>
04) Restituisce la List<Player>, verrà usata come argomento del metodo aggornaListaPlayer() di Leaderboard
05) Inserisce un nuovo score nel database
*/