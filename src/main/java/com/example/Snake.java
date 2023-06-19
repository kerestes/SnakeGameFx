package com.example;

import java.util.LinkedList;
import javafx.scene.shape.Rectangle;

public class Snake {

    private char direction = 'D';
    private LinkedList<Rectangle> listRect = new LinkedList<>();

    public void setListRect(LinkedList<Rectangle> listRect) {
        this.listRect = listRect;
    }

    public LinkedList<Rectangle> getListRect() {
        return listRect;
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
        listRect.add(rect);

        for(int i=3; i>=0; i--){
            rect = new Rectangle(38, 38);
            rect.getStyleClass().add("rect");
            rect.setX(i);
            rect.setY(0);
            listRect.addLast(rect);
        }
    }

    public boolean mouvement(Rectangle nourriture) {
        int[] dir = new int[2]; 
        switch(direction){
            case 'D':
                dir[0] = 1;
                dir[1] = 0;
                if(verifierLimites(dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setX(listRect.get(0).getX()+dir[0]);
                    return true;
                }
                return false;
            case 'G':
                dir[0] = -1;
                dir[1] = 0;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setX(listRect.get(0).getX()+dir[0]);
                    return true;
                }
                return false;
            case 'H':
                dir[0] = 0;
                dir[1] = -1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setY(listRect.get(0).getY()+dir[1]);
                    return true;
                }
                return false;
            case 'B':
                dir[0] = 0;
                dir[1] = 1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setY(listRect.get(0).getY()+dir[1]);
                    return true;
                }
                return false;
        }
        return false;
        
    }

    public void mouvement(Rectangle nourriture, char directionJouer) {
        int[] dir = new int[2]; 
        switch(directionJouer){
            case 'D':
                dir[0] = 1;
                dir[1] = 0;
                if(verifierLimites(dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setX(listRect.get(0).getX()+dir[0]);
                }
                break;
            case 'G':
                dir[0] = -1;
                dir[1] = 0;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setX(listRect.get(0).getX()+dir[0]);
                }
                break;
            case 'H':
                dir[0] = 0;
                dir[1] = -1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setY(listRect.get(0).getY()+dir[1]);
                }
                break;
            case 'B':
                dir[0] = 0;
                dir[1] = 1;
                if(verifierLimites( dir, nourriture)){
                    for(int i=listRect.size()-1; i>0; i--){
                        listRect.get(i).setX(listRect.get(i-1).getX());
                        listRect.get(i).setY(listRect.get(i-1).getY());
                    }
                    listRect.get(0).setY(listRect.get(0).getY()+dir[1]);
                }
                break;
        }
        direction = directionJouer;
        
    }

    private boolean verifierLimites(int[] dir, Rectangle nourriture) {
        if(listRect.get(0).getX()+dir[0] > 19 || listRect.get(0).getX()+dir[0] < 0 || listRect.get(0).getY()+dir[1] > 13 || listRect.get(0).getY()+dir[1] < 0){
            return false;
        }
        for (int i=1; i<listRect.size(); i++){
            if (listRect.get(i).getX() == listRect.get(0).getX()+dir[0] && listRect.get(i).getY() == listRect.get(0).getY()+dir[1]){
                return false;
            }
        }

        if (listRect.get(0).getX()+dir[0] == nourriture.getX() && listRect.get(0).getY()+dir[1] == nourriture.getY()){
            Rectangle rect;
            rect = new Rectangle(38, 38);
            rect.getStyleClass().add("rect");
            rect.setX(listRect.get(listRect.size()-1).getX());
            rect.setY(listRect.get(listRect.size()-1).getY());
            listRect.addLast(rect);
        }
        return true;
    }


}
