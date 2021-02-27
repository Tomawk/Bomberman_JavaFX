public class Score {
    public int scoreFinale,secondiRimasti; //01
    private int scoreTempo; //02
    private final int scoreNemico;
    private String stringaScore;
   
    public Score(String difficolta){ //00
        
        scoreFinale = 0;
        scoreNemico = 2000;
        if(difficolta.equals("FACILE")) { scoreTempo = 100; secondiRimasti = 150; } 
        if(difficolta.equals("INTERMEDIO")) { scoreTempo = 200; secondiRimasti = 120; }
        if(difficolta.equals("DIFFICILE")) { scoreTempo = 300; secondiRimasti = 90; }
        
    }
    
    public void calcolaScoreFinale(){ scoreFinale = scoreFinale + scoreTempo*secondiRimasti; } //03
    
    public void incrementaScoreNemico() { scoreFinale = scoreFinale + scoreNemico; }  //04
    
    public String aggiornaLabelScore() { //05
        stringaScore = "";
        int lengthScore = String.valueOf(scoreFinale).length();
        int quantiZeri = 8 - lengthScore;
        for(int i=0; i<quantiZeri; i++){
            stringaScore =  stringaScore + "0";
        }
        stringaScore = stringaScore + String.valueOf(scoreFinale);
        return stringaScore;
    }
    
    public int getScoreNumber(){ return scoreFinale;}
}

/* Note:

00) Classe per la gestione del punteggio del livello
01) scoreFinale: variabile che contiene lo score finale quando viene vinto un livello
    secondiRimasti: numero di secondi rimasti per completare il livello, viene decrementato
    dalla Timeline del timer e mi serve in quanto il punteggio finale è calcolato attribuendo
    un punteggio predefinito a ciascun secondo rimanente quando viene completato il livello
02) Valore predefinito in base alla difficoltà scelta da attribuire a ciascun secondo rimanente del livello
03) Funzione chiamata una volta completato il livello, mi aggiorna scoreFinale in base ai secondi rimanenti
04) Funzione chiamata ogni volta che viene ucciso un nemico, ogni nemico garantisce infatti un bonus di 2000 punti
05) Funzione passata come argomento ai metodi di GestoreMessaggi per stampare la stringa del punteggio corrispondente
    al punteggio finale, viene formattata in modo che sia composta da 8 caratteri (quelli in eccesso sono 0)
*/