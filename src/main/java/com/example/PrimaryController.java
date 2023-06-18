package com.example;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class PrimaryController implements Initializable{

    private Snake snake = new Snake();
    private Bloque bloque = new Bloque();
    private char dir = 'D';
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
    
    public void clavier(KeyEvent k){
        
        switch(k.getCode()){
            case UP:
                if(snake.getDirection() != 'B'){
                    dir = 'H';
                }
                break;
            case DOWN:
                if(snake.getDirection() != 'H'){
                    dir = 'B';
                }
                break;
            case LEFT:
                if(snake.getDirection() != 'D'){
                    dir = 'G';
                }
                break;
            case RIGHT:
                if(snake.getDirection() != 'G'){
                    dir = 'D';
                }
                break;
        }
    }
    
    public void demarrerJeu(ActionEvent e){
        
        principal.setVisible(false);
        demarrer.setVisible(false);
        point.setVisible(true);
        point.setText("Point : " + valeurPoint);
        gp.setVisible(true);
        ap.requestFocus();

        snake.creerSerpent();
        for(int i=0; i<snake.getListRect().size(); i++){
            gp.add(snake.getListRect().get(i), (int)snake.getListRect().get(i).getX(), (int)snake.getListRect().get(i).getY());
        }

        bloque.mouvementBloque(snake.getListRect());
        bloque.getNourriture().setId("nourriture");
        gp.add(bloque.getNourriture(), (int) bloque.getNourriture().getX(), (int) bloque.getNourriture().getY());

        declancherTimer();
    }

    public void declancherTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(()->{
                    snake.setDirection(dir);
                    if(snake.mouvement(bloque.getNourriture())){
                        if(snake.getListRect().get(0).getX() == bloque.getNourriture().getX() && snake.getListRect().get(0).getY() == bloque.getNourriture().getY()){
                            bloque.mouvementBloque(snake.getListRect());
                            gp.setColumnIndex(bloque.getNourriture(), (int) bloque.getNourriture().getX());
                            gp.setRowIndex(bloque.getNourriture(), (int) bloque.getNourriture().getY());
                            valeurPoint += 10;
                            point.setText("Point : " + valeurPoint);
                        }
                        gp.getChildren().clear();
                        for(Rectangle r: snake.getListRect()){
                            gp.add(r, (int)r.getX(), (int)r.getY());
                        }
                        gp.add(bloque.getNourriture(), (int) bloque.getNourriture().getX(), (int) bloque.getNourriture().getY());
                    } else {
                        timer.cancel();
                        finJeu();
                    }
                });
            }
            
        }, 100, 300);
    }

    public void finJeu(){
        gp.getChildren().clear();
        finJeu.setVisible(true);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("entrou?");
                gp.setVisible(false);
                demarrer.setVisible(true);
                principal.setVisible(true);
                point.setVisible(false);
                finJeu.setVisible(false);
                timer.cancel();
            }
            
        }, 2000);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        gp.setVisible(false);
        point.setVisible(false);
        finJeu.setVisible(false);
    }

}
