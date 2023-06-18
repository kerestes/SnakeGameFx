package com.example;

import java.util.LinkedList;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class NodeDijkstra {

    private int[][] corpSnake;
    private char directionNode;
    private NodeDijkstra nodePere = null;
    private boolean visite = false;
    private boolean bloque = false;

    public NodeDijkstra(LinkedList<Rectangle> snake, char dir){
        corpSnake = new int[snake.size()][2];
        for(int i=0; i<snake.size(); i++){
            corpSnake[i][0] = (int) snake.get(i).getX();
            corpSnake[i][1] = (int) snake.get(i).getY();
            directionNode = dir;
        }
    }

    public NodeDijkstra(int[][] corpSnake, char dir, int[] nouvellePosition, NodeDijkstra nodePere){
        this.corpSnake = new int[corpSnake.length][2];
        this.corpSnake[0][0] = corpSnake[0][0] + nouvellePosition[0];
        this.corpSnake[0][1] = corpSnake[0][1] + nouvellePosition[1];
        for(int i=1; i<corpSnake.length; i++){
            this.corpSnake[i][0] = corpSnake[i-1][0];
            this.corpSnake[i][1] = corpSnake[i-1][1];
        }
        this.directionNode = dir;
        this.nodePere = nodePere;
    }

    public char getDirectionNode() {
        return directionNode;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public boolean getBloque(){
        return bloque;
    }

    public void setVisite(boolean visite) {
        this.visite = visite;
    }

    public boolean getVisite(){
        return visite;
    }

    public int[][] getCorpSnake() {
        return corpSnake;
    }

    public NodeDijkstra getNodePere() {
        return nodePere;
    }
    
}
