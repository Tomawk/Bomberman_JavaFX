
public class GestoreCollisioni {
    
    private final InterfacciaStatica interfacciaStatica;
    private final InterfacciaDinamica interfacciaDinamica;
    private final GestoreSuoni gestoreSuoni;
    
    
    public GestoreCollisioni( 
            InterfacciaStatica interfacciaS,
            InterfacciaDinamica interfacciaD,
            GestoreSuoni gestoreSounds
    ){
        gestoreSuoni = gestoreSounds;
        interfacciaStatica = interfacciaS;
        interfacciaDinamica = interfacciaD;
    }
    
    void gestisciCollisioneRocce(Character personaggio,int dx1, int dy1){ 
        for(int i=0; i<30; i++){ //01
           if(personaggio.getCircle().getBoundsInParent().intersects(interfacciaStatica.getListRoccia().get(i).getBoundsInLocal())) { //02
              personaggio.moveHeroBy(dx1, dy1); //03
            }
        
        }
    }
    
    void gestisciCollisioneMassi( Character personaggio, int dx1, int dy1){ //04
         for(int i=0; i < interfacciaDinamica.getListMassi().size(); i++){ 
            if(personaggio.getCircle().getBoundsInParent().intersects(interfacciaDinamica.getListMassi().get(i).getBoundsInLocal())) {
               personaggio.moveHeroBy(dx1, dy1); 
            }
                   
         }
    }
    
    void gestisciCollisioniBombaMassi(Character personaggio,Score score, FileConfigurazioneXML config){
        for(int i=0; i< interfacciaDinamica.getListMassi().size(); i++){ 
            
            if(personaggio.getBomb().getRettangolo1().getBoundsInParent().intersects(interfacciaDinamica.getListMassi().get(i).getBoundsInLocal()) || 
               personaggio.getBomb().getRettangolo2().getBoundsInParent().intersects(interfacciaDinamica.getListMassi().get(i).getBoundsInLocal())) 
            {   //05
               interfacciaDinamica.getListMassi().remove(i); 
               interfacciaStatica.getContenitore().getChildren().remove(interfacciaDinamica.getArrayMassi().get(i).getImageViewMasso()); //06
               if(interfacciaDinamica.getArrayMassi().get(i).containsDoor == true && interfacciaDinamica.getSecretDoor().isFound == false) { //07
                  interfacciaDinamica.getSecretDoor().isFound = true; 
                  GestoreLogAttivitaXML.creaLog("Porta Segreta Trovata!", config);
                  interfacciaStatica.getContenitore().getChildren().add(interfacciaDinamica.getSecretDoor().getImageViewDoor());
                  interfacciaDinamica.getSecretDoor().getImageViewDoor().relocate(
                          interfacciaDinamica.getArrayMassi().get(i).getImageViewMasso().getLayoutX(),
                          interfacciaDinamica.getArrayMassi().get(i).getImageViewMasso().getLayoutY()
                  );
              }
              interfacciaDinamica.getArrayMassi().remove(i);
              score.scoreFinale += 100; //08
              interfacciaStatica.getScoreLabel().setText(score.aggiornaLabelScore());
            }
        }  
    }
    
    boolean gestisciCollisioneBombaCharacter(Character personaggio, GestoreMessaggi messaggio, int timeleft){
        
        if(personaggio.getBomb().getRettangolo1().getBoundsInParent().intersects(personaggio.getCircle().getBoundsInParent()) ||
           personaggio.getBomb().getRettangolo2().getBoundsInParent().intersects(personaggio.getCircle().getBoundsInParent()))
        {
           personaggio.dead();
           messaggio.DeathMessage(interfacciaStatica.getScoreLabel().getText(),timeleft,interfacciaStatica);
           gestoreSuoni.avviaMusicaGameOver();
           return true;
        }
        return false;
    }
    
    boolean gestisciCollisioneCharacterDoor(Character personaggio, Score score, GestoreMessaggi messaggio, int timeleft){ //09
        if(personaggio.getCircle().getBoundsInParent().intersects(interfacciaDinamica.getSecretDoor().getImageViewDoor().getBoundsInParent())){
          gestoreSuoni.avviaMusicaVittora();
          score.scoreFinale += 5000; //10
          interfacciaStatica.getScoreLabel().setText(score.aggiornaLabelScore());
          personaggio.ready = false;
          score.calcolaScoreFinale(); //11
          messaggio.CongratsMessage(score.aggiornaLabelScore(),timeleft,interfacciaStatica);
          return true;
        }
        return false;
    }
    
    
    boolean gestisciCollisioneNemiciMassi(Enemy nemico,int dx, int dy){
        for(int i=0; i < interfacciaDinamica.getListMassi().size(); i++){ 
            if(nemico.getEnemyImageView().getBoundsInParent().intersects(interfacciaDinamica.getListMassi().get(i).getBoundsInLocal())) {
               nemico.moveEnemyBy(dx, dy); 
               return false;
            }
        }
        return true;
    }
    
   boolean gestisciCollisioneNemiciRocce(Enemy nemico,int dx, int dy){
        for(int i=0; i < 30; i++){ 
            if(nemico.getEnemyImageView().getBoundsInParent().intersects(interfacciaStatica.getListRoccia().get(i).getBoundsInLocal())) {
               nemico.moveEnemyBy(dx, dy); 
               return false;
            }
        }
        return true;
    }
   
   boolean gestisciCollisioneNemiciDoor(Enemy nemico,int dx, int dy){
       if(nemico.getEnemyImageView().getBoundsInParent().intersects(interfacciaDinamica.getSecretDoor().getImageViewDoor().getBoundsInParent())){
           nemico.moveEnemyBy(dx, dy);
           return false;
       }
       return true;
   }
   
   boolean gestisciCollisioneNemiciBomberman(Character personaggio, GestoreMessaggi messaggio, int timeleft){ //12
       for(int i=0; i<interfacciaDinamica.getArrayNemici().size(); i++){
           if(personaggio.getCircle().getBoundsInParent().intersects(interfacciaDinamica.getArrayNemici().get(i).getEnemyImageView().getBoundsInParent())){
               personaggio.dead();
               gestoreSuoni.avviaMusicaGameOver();
               messaggio.DeathMessage(interfacciaStatica.getScoreLabel().getText(),timeleft,interfacciaStatica); //13
               return true;
           }
       }
       return false;
   }
   
   void gestisciCollisioneBombaNemici(Character personaggio, Score score, FileConfigurazioneXML config){
        for(int i=0; i< interfacciaDinamica.getArrayNemici().size(); i++){ //14
            if(personaggio.getBomb().getRettangolo1().getBoundsInParent().intersects(interfacciaDinamica.getArrayNemici().get(i).getEnemyImageView().getBoundsInParent()) ||
               personaggio.getBomb().getRettangolo2().getBoundsInParent().intersects(interfacciaDinamica.getArrayNemici().get(i).getEnemyImageView().getBoundsInParent())) 
            {
               GestoreLogAttivitaXML.creaLog("Nemico Ucciso", config);
               gestoreSuoni.avviaSuonoDeadEnemy();
               interfacciaStatica.getContenitore().getChildren().remove(interfacciaDinamica.getArrayNemici().get(i).getEnemyImageView());
               interfacciaDinamica.getArrayNemici().remove(i);
               score.scoreFinale += 500; //15
               interfacciaStatica.getScoreLabel().setText(score.aggiornaLabelScore());
            }
        }
    }
   
}

/*Note

01) Controllo per ogni roccia se faccio collisione con Bomberman
02) Controllo se l'Imageview del "fulcro" di Bomberman si interseca con il quadrato 40x40px sotto ogni roccia
03) Se c'è l'intersezione annullo il movimento con un movimento opposto, uscendo dalla collisione
04) Controllo per ogni masso se faccio collisione con Bomberman
05) Il raggio di esplosione della bomba è una croce formata da due rettangoli, uno orizzontale e uno verticale
06) Rimuovo il masso e il rettangolo ad esso associato in caso di collisione con l'esplosione
07) In caso il masso sia distrutto e al suo interno sia presente la botola, essa viene mostrata
08) Ogni masso distrutto garantisce un bonus al punteggio
09) Collisione Bomberman e la botola : Vittoria
10) Bonus al punteggio in caso di vittoria
11) Calcolo il punteggio finale in base al tempo rimanente del livello
12) Collisione Bomberman e nemico : Game Over
13) Viene mostrato a video un messaggio di Game Over
14) Collisione Esplosione e nemico
15) Bonus al punteggio in caso di nemico ucciso
*/