package com.example;

import java.util.LinkedList;
import javafx.scene.shape.Rectangle;

public class Snake {

    private char direction = 'D';
    private LinkedList<Rectangle> listRectanglesSnake = new LinkedList<>();

    public void setListRectanglesSnake(LinkedList<Rectangle> listRectanglesSnake) {
        this.listRectanglesSnake = listRectanglesSnake;
    }

    public LinkedList<Rectangle> getListRectanglesSnake() {
        return listRectanglesSnake;
    }

    public void setDirection(char direction){
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public void creerSerpent(){
        Rectangle rect;
        
        rect = new Rectangle(38, 38);
        rect.setId("tete");
        rect.setX(4);
        rect.setY(0);
        listRectanglesSnake.add(rect);

        for(int i=3; i>=0; i--){
            rect = new Rectangle(38, 38);
            rect.getStyleClass().add("rect");
            rect.setX(i);
            rect.setY(0);
            listRectanglesSnake.addLast(rect);
        }
    }

    public boolean mouvementJourHumain(Rectangle nourriture) {
        //Pour vérifier l'espace avant de place le snake là-bas
        int[] dir = new int[2]; 

        switch(direction){
            case 'D':
                dir[0] = 1;
                dir[1] = 0;
                //La fonction verifierLimites gère les limites du tableau (return false), la possibilité du snake toucher lui-même (returne false) et si le snake retrouve la nourriture (tire au sort un nouvel endroit et augmente le serpent)
                if(verifierLimites(dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setX(listRectanglesSnake.get(0).getX()+dir[0]);
                    return true;
                }
                return false;
            case 'G':
                dir[0] = -1;
                dir[1] = 0;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setX(listRectanglesSnake.get(0).getX()+dir[0]);
                    return true;
                }
                return false;
            case 'H':
                dir[0] = 0;
                dir[1] = -1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setY(listRectanglesSnake.get(0).getY()+dir[1]);
                    return true;
                }
                return false;
            case 'B':
                dir[0] = 0;
                dir[1] = 1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setY(listRectanglesSnake.get(0).getY()+dir[1]);
                    return true;
                }
                return false;
        }
        return false;
        
    }

    public void mouvementJouerDijkstra(Rectangle nourriture, char directionJouer) {
        
        int[] dir = new int[2]; 
        switch(directionJouer){
            case 'D':
                dir[0] = 1;
                dir[1] = 0;
                //La seule utilité da fonction verifierLimites dans ce cas est d'agrandir le Serpent
                if(verifierLimites(dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setX(listRectanglesSnake.get(0).getX()+dir[0]);
                }
                break;
            case 'G':
                dir[0] = -1;
                dir[1] = 0;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setX(listRectanglesSnake.get(0).getX()+dir[0]);
                }
                break;
            case 'H':
                dir[0] = 0;
                dir[1] = -1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setY(listRectanglesSnake.get(0).getY()+dir[1]);
                }
                break;
            case 'B':
                dir[0] = 0;
                dir[1] = 1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRectanglesSnake.size()-1; i>0; i--){
                        listRectanglesSnake.get(i).setX(listRectanglesSnake.get(i-1).getX());
                        listRectanglesSnake.get(i).setY(listRectanglesSnake.get(i-1).getY());
                    }
                    listRectanglesSnake.get(0).setY(listRectanglesSnake.get(0).getY()+dir[1]);
                }
                break;
        }
        direction = directionJouer;
        
    }

    private boolean verifierLimites(int[] dir, Rectangle nourriture) {
        //Elle vérifie les limites
        if(listRectanglesSnake.get(0).getX()+dir[0] > 19 || listRectanglesSnake.get(0).getX()+dir[0] < 0 || listRectanglesSnake.get(0).getY()+dir[1] > 13 || listRectanglesSnake.get(0).getY()+dir[1] < 0){
            return false;
        }
        //Elle vérifie si le snake n'a pas touché sa queue
        for (int i=1; i<listRectanglesSnake.size(); i++){
            if (listRectanglesSnake.get(i).getX() == listRectanglesSnake.get(0).getX()+dir[0] && listRectanglesSnake.get(i).getY() == listRectanglesSnake.get(0).getY()+dir[1]){
                return false;
            }
        }

        //Elle Vérifie se le snake a rencontré la nourriture
        if (listRectanglesSnake.get(0).getX()+dir[0] == nourriture.getX() && listRectanglesSnake.get(0).getY()+dir[1] == nourriture.getY()){
            Rectangle rect;
            rect = new Rectangle(38, 38);
            rect.getStyleClass().add("rect");
            rect.setX(listRectanglesSnake.get(listRectanglesSnake.size()-1).getX());
            rect.setY(listRectanglesSnake.get(listRectanglesSnake.size()-1).getY());
            listRectanglesSnake.addLast(rect);
        }
        return true;
    }


}
