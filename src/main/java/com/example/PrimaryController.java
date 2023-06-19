package com.example;

import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class PrimaryController implements Initializable{

    private Snake snake = new Snake();
    private Bloque bloqueNourriture = new Bloque();
    private Jouer jouer = new Jouer(snake, bloqueNourriture);
    private char directionSnake = 'D';
    private int valeurPoint = 0;
    private Timer timer;

    @FXML
    private AnchorPane ap;
    @FXML
    private GridPane gp;
    @FXML
    private Label point;
    @FXML
    private Label finJeu;
    @FXML
    private Label principal;
    @FXML
    private Button demarrer;
    
    public void entreeClavier(KeyEvent k){
        
        switch(k.getCode()){
            case UP:
                if(snake.getDirection() != 'B'){
                    directionSnake = 'H';
                }
                break;
            case DOWN:
                if(snake.getDirection() != 'H'){
                    directionSnake = 'B';
                }
                break;
            case LEFT:
                if(snake.getDirection() != 'D'){
                    directionSnake = 'G';
                }
                break;
            case RIGHT:
                if(snake.getDirection() != 'G'){
                    directionSnake = 'D';
                }
                break;
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        point.setVisible(false);
        finJeu.setVisible(false);
        
        demarrerJeuAvecDijkstra();
        
    }

    public void demarrerJeuAvecDijkstra(){
        
        creerGridPane();
        
        jouer.trouverChemin();
        
        declancherTimerAvecDijkstra();
    }

    public void creerGridPane(){

        snake.creerSerpent();
        
        snake.setDirection('D');
        directionSnake = 'D';
        
        for(Rectangle r: snake.getListRectanglesSnake()){
            gp.add(r, (int)r.getX(), (int)r.getY());
        }

        bloqueNourriture.mouvementBloque(snake.getListRectanglesSnake());

        gp.add(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getX(), (int) bloqueNourriture.getNourriture().getY());
    }

    public void declancherTimerAvecDijkstra(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(()->{
                    if(jouer.getListMouv().size() == 0){
                        //fin de Jeu
                        timer.cancel();
                        // Rédemarre la liste Snake et le GridPane
                        gp.getChildren().clear();
                        snake.getListRectanglesSnake().clear();
                        //Rédemarre le jeu en soi
                        demarrerJeuAvecDijkstra();

                    } else {                

                        snake.mouvementJouerDijkstra(bloqueNourriture.getNourriture(), jouer.getListMouv().removeFirst());

                        if(jouer.getListMouv().size() == 0){
                            //S'il n'y a plus de mouvement ça veut dire que le snake à trouver la nourriture, donc il faut en tirer au sort un nouveau endroit et refaire la route vers la nourriture

                            bloqueNourriture.mouvementBloque(snake.getListRectanglesSnake());
                            
                            gp.setColumnIndex(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getX());
                            
                            gp.setRowIndex(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getY());

                            jouer.trouverChemin();
                        }

                        //Il repeint la snène 
                        gp.getChildren().clear();
                        for(Rectangle r: snake.getListRectanglesSnake()){
                            gp.add(r, (int)r.getX(), (int)r.getY());
                        }

                        if (snake.getListRectanglesSnake().size() < 280){
                            gp.add(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getX(), (int) bloqueNourriture.getNourriture().getY());     
                        }
                    }
                });
            }

        }, 100, 100);
    }
    
    //bouton démarrer
    public void demarrerJeu(ActionEvent e){

        //il arrête le Dijkstra
        timer.cancel();

        //Redémarre la liste du snake et le GridPane
        snake.getListRectanglesSnake().clear();
        gp.getChildren().clear();
        
        //Paramétrage du écran
        principal.setVisible(false);
        demarrer.setVisible(false);
        point.setVisible(true);
        point.setText("Point : " + valeurPoint);
        gp.setStyle("-fx-background-color: white");

        //Demande le focus au AnchorPane pour pouvoir accepter les flèches du Clavier
        ap.requestFocus();

        creerGridPane();

        declancherTimer();
    }

    public void declancherTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(()->{

                    //Il faut bien contrôler la direction, sinon le serpent peut se retrouver sur elle-même.
                    snake.setDirection(directionSnake);
                    
                    //La function mouvement returne un boolean, et de cette façon nous pouvons savoir si le snake a atteint la limite où s'est touché elle-même.
                    if(snake.mouvementJourHumain(bloqueNourriture.getNourriture())){
                        //vérifier s'il a rencontré la nourriture
                        if(snake.getListRectanglesSnake().get(0).getX() == bloqueNourriture.getNourriture().getX() && snake.getListRectanglesSnake().get(0).getY() == bloqueNourriture.getNourriture().getY()){
                            bloqueNourriture.mouvementBloque(snake.getListRectanglesSnake());
                            gp.setColumnIndex(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getX());
                            gp.setRowIndex(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getY());
                            valeurPoint += 10;
                            point.setText("Point : " + valeurPoint);
                        }

                        //repeint la scène
                        gp.getChildren().clear();
                        for(Rectangle r: snake.getListRectanglesSnake()){
                            gp.add(r, (int)r.getX(), (int)r.getY());
                        }

                        gp.add(bloqueNourriture.getNourriture(), (int) bloqueNourriture.getNourriture().getX(), (int) bloqueNourriture.getNourriture().getY());

                    } else {
                        timer.cancel();
                        finJeu();
                    }
                });
            }
            
        }, 100, 300);
    }

    public void finJeu(){
        gp.setStyle("-fx-background-color: #ffffb6");

        gp.getChildren().clear();
        snake.getListRectanglesSnake().clear();

        finJeu.setVisible(true);

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(()->{
                    demarrer.setVisible(true);
                    principal.setVisible(true);
                    point.setVisible(false);
                    finJeu.setVisible(false);
                    timer.cancel();
                    demarrerJeuAvecDijkstra();
                });
            }
            
        }, 2000);
    }
}
