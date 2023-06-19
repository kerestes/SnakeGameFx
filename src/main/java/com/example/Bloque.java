package com.example;

import java.util.LinkedList;
import java.util.Random;

import javafx.scene.shape.Rectangle;

public class Bloque {


    private Random random = new Random();
    private int x;
    private int y;
    private boolean verifierQueue;
    private Rectangle nourriture = new Rectangle(38, 38);

    public Bloque(){
        nourriture.setId("nourriture");
    }

    public void setNourriture(Rectangle nourriture) {
        this.nourriture = nourriture;
    }

    public Rectangle getNourriture() {
        return nourriture;
    }

    public void mouvementBloque(LinkedList<Rectangle> listRect){
        //si toutes les cases sont occupées il n'y a plus de espace pour le snake
        if (listRect.size() != 280){
            do{
                verifierQueue = true;
                x = random.nextInt(20);
                y = random.nextInt(14);
                for(Rectangle r: listRect){
                    if(r.getX() == x && r.getY() == y){
                        verifierQueue = false;
                    }
                }
            }while(!verifierQueue);
        } 
        nourriture.setX(x);
        nourriture.setY(y);
    }

}
