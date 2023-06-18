package com.example;

import java.util.LinkedList;

public class Jouer{

    private Snake snake;
    private Bloque bloque;
    private LinkedList<NodeDijkstra> nd = new LinkedList<>();
    private LinkedList<Character> listMouv = new LinkedList<>();

    private boolean finNodeDijkstra = false;
    private boolean bloqueTrouve;

    public Jouer(Snake snake, Bloque bloque){
        this.snake = snake;
        this.bloque = bloque;
    }

    public LinkedList<Character> getListMouv() {
        return listMouv;
    }

    public void trouverChemin(){

        nd.clear();
        bloqueTrouve = false;
        finNodeDijkstra = false;

        nd.add(new NodeDijkstra(snake.getListRect(), snake.getDirection()));

        while(!finNodeDijkstra){
            finNodeDijkstra = true;
            for(NodeDijkstra n: nd){
                if(!n.getVisite()){
                    finNodeDijkstra = false;
                    verifierDirection(n);
                    if (bloqueTrouve){
                        finNodeDijkstra = false;
                    }
                    break;
                }
            }
        }

        for(NodeDijkstra n: nd){
            if(n.getBloque() ==true){
                NodeDijkstra nodeNourriture = n;
                while(nodeNourriture.getNodePere() != null){
                    listMouv.addFirst(nodeNourriture.getDirectionNode());
                    nodeNourriture = nodeNourriture.getNodePere();
                }
                break;
            }
        }

    }

    public void verifierDirection(NodeDijkstra node){
        switch(node.getDirectionNode()){
            case 'D':
                verifierLimites(node, 'H');
                verifierLimites(node, 'D');
                verifierLimites(node, 'B');
                node.setVisite(true);
                break;
            case 'G':
                verifierLimites(node, 'B');
                verifierLimites(node, 'G');
                verifierLimites(node, 'H');
                node.setVisite(true);
                break;
            case 'H':
                verifierLimites(node, 'D');
                verifierLimites(node, 'H');
                verifierLimites(node, 'G');
                node.setVisite(true);
                break;
            case 'B':
                verifierLimites(node, 'G');
                verifierLimites(node, 'B');
                verifierLimites(node, 'D');
                node.setVisite(true);
                break;
        }
    }

    public boolean verifierLimites(NodeDijkstra nodePere, char nodeFilsDirection){
        int[] nouvellePosition = new int[2];
        switch(nodeFilsDirection){
            case 'D':
                nouvellePosition[0] = 1;
                nouvellePosition[1] = 0;
                break;
            case 'G':
                nouvellePosition[0] = -1;
                nouvellePosition[1] = 0;
                break;
            case 'H':
                nouvellePosition[0] = 0;
                nouvellePosition[1] = -1;
                break;
            case 'B':
                nouvellePosition[0] = 0;
                nouvellePosition[1] = 1;
                break;
        }
       
        if(nodePere.getCorpSnake()[0][0] + nouvellePosition[0] > 19 || nodePere.getCorpSnake()[0][0]+nouvellePosition[0] < 0 || nodePere.getCorpSnake()[0][1] + nouvellePosition[1] > 13 ||
        nodePere.getCorpSnake()[0][1] + nouvellePosition[1] < 0){
            return false;
        }
        for (int i=0; i<nodePere.getCorpSnake().length; i++){
            if(nodePere.getCorpSnake()[i][0] == nodePere.getCorpSnake()[0][0] + nouvellePosition[0] && nodePere.getCorpSnake()[i][1] == nodePere.getCorpSnake()[0][1] + nouvellePosition[1] ){
                return false;
            }
        }
        for(NodeDijkstra n: nd){
            if(n.getCorpSnake()[0][0] == nodePere.getCorpSnake()[0][0] + nouvellePosition[0] && n.getCorpSnake()[0][1] == nodePere.getCorpSnake()[0][1] + nouvellePosition[1]){
                return false;
            }
        }

        nd.addLast(new NodeDijkstra(nodePere.getCorpSnake(), nodeFilsDirection, nouvellePosition, nodePere));

        if(nd.getLast().getCorpSnake()[0][0] == bloque.getNourriture().getX() && nd.getLast().getCorpSnake()[0][1] == bloque.getNourriture().getY()){
            nd.getLast().setBloque(true);
            bloqueTrouve = true;
        }
        return true;
    }
}