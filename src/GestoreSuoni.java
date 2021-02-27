
import java.io.*;
import javafx.scene.media.*;
import javafx.util.*;


public class GestoreSuoni {
    private static final String TITLE_SOUND = "file:../../AddOn/Sounds/Title_Screen.mp3", //01
                         GAME_SOUND = "file:../../AddOn/Sounds/Find_The_Door.mp3",  //01
                         GAMEOVER_SOUND = "file:../../AddOn/Sounds/Game_Over.mp3",  //01
                         WINNING_SOUND = "file:../../AddOn/Sounds/Level_Complete.mp3",  //01
                         ENEMYDEAD_SOUND = "file:../../AddOn/Sounds/Enemy_Death.wav",  //01
                         BOMB_SOUND = "file:../../AddOn/Sounds/Explosion.wav",  //01
                         TIMER_SOUND = "file:../../AddOn/Sounds/Timer.mp3";  //01
    
    private final MediaPlayer mediaPlayerEnemy, mediaPlayerBomb, mediaPlayerTimer; //02
    private MediaPlayer mediaPlayerSoundtrack; //02
    
    public GestoreSuoni(){
        mediaPlayerSoundtrack = new MediaPlayer(new Media(new File(TITLE_SOUND).toURI().toString())); //03
        mediaPlayerBomb = new MediaPlayer(new Media(new File(BOMB_SOUND).toURI().toString()));  //03
        mediaPlayerTimer = new MediaPlayer(new Media(new File(TIMER_SOUND).toURI().toString())); //03
        mediaPlayerEnemy = new MediaPlayer(new Media(new File(ENEMYDEAD_SOUND).toURI().toString())); //03
        mediaPlayerBomb.setVolume(0.5);
        
    }
    
    public void avviaMusicaTitle(){ //04
        mediaPlayerSoundtrack.play();
        mediaPlayerSoundtrack.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayerSoundtrack.seek(Duration.ZERO);
            }
        });
    }
    
    public void avviaMusicaGioco(){ //04
        mediaPlayerSoundtrack.stop();
        mediaPlayerSoundtrack = new MediaPlayer(new Media(new File(GAME_SOUND).toURI().toString()));
        mediaPlayerSoundtrack.play();
    }
    
    public void avviaMusicaGameOver(){ //04
        mediaPlayerSoundtrack.stop();
        mediaPlayerSoundtrack = new MediaPlayer(new Media(new File(GAMEOVER_SOUND).toURI().toString()));
        mediaPlayerSoundtrack.play();
    }
    
    public void avviaMusicaVittora(){ //04
        mediaPlayerSoundtrack.stop();
        mediaPlayerSoundtrack = new MediaPlayer(new Media(new File(WINNING_SOUND).toURI().toString()));
        mediaPlayerSoundtrack.play();
    }
    
    public void avviaSuonoDeadEnemy(){ //05
        mediaPlayerEnemy.stop();
        mediaPlayerEnemy.play();
    }
    
    public void avviaSuonoBomba(){ //05
        mediaPlayerBomb.stop();
        mediaPlayerBomb.play();
    }
    
    public void avviaSuonoTimer(){ //05
        mediaPlayerTimer.play();
    }
    
    public void interrompiSuoni(){ //06
        mediaPlayerTimer.stop();
        mediaPlayerEnemy.stop();
        mediaPlayerBomb.stop();
    }
}

/* Note:

01) Path da cui prelevare i file mp3/wav
02) MediaPlayer per la riproduzione dei Media
03) Associo a ciascun MediaPlayer un Media relativo ai file sonori mp3/wav
    Non vengono gestiti tutti i media con un solo mediaPlayer in quanto in alcuni casi
    c'è una sovrapposizione di suoni da mantenere
04) Metodi che coinvolgono solo mediaPlayerSoundtrack, lo stoppano se era precedentemente attivo,
    ci associano un nuovo media da riprodurre e avviano il mediaPlayer
05) Metodi che coinvolgono MediaPlayer specifici:
    avviaSuonoDeadEnemy() : viene riprodotto quando un nemico viene ucciso, 
    può essere riprodotto piu volte in una partita (per questo è preceduto dalla stop())
    avviaSuonoBomba() : viene riprodotto quando la bomba è esplosa, può essere riprodotto più volte a partita
    avviaSuonoTImer() : viene riprodotto quando il timer scende sotto i 9 secondi (il tempo sta per esaurirsi)
06) Interrompe eventuali suoni attivi, viene chiamato dalla funzione reset() di Bomberman

*/