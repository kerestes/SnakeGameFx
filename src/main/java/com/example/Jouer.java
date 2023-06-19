package com.example;

import java.util.LinkedList;

public class Jouer{

    private Snake snake;
    private Bloque bloqueNourriture;
    private LinkedList<NodeDijkstra> listNodeDijstra = new LinkedList<>();
    private LinkedList<Character> listMouv = new LinkedList<>();

    private boolean finListNodeDijkstra = false;
    private boolean bloqueTrouve;

    public Jouer(Snake snake, Bloque bloqueNourriture){
        this.snake = snake;
        this.bloqueNourriture = bloqueNourriture;
    }

    public LinkedList<Character> getListMouv() {
        return listMouv;
    }

    public void trouverChemin(){

        //Réinitialise la liste de NodeDijkstra et la liste de Mouvement
        listNodeDijstra.clear();
        listMouv.clear();

        bloqueTrouve = false;
        finListNodeDijkstra = false;

        //il crée le prémier NodeDikstra dans la liste, c'est la tête du Snake
        listNodeDijstra.add(new NodeDijkstra(snake.getListRectanglesSnake(), snake.getDirection()));

        //il ajoute de nouveaux NodeDijkstra dans la liste jusqu'au moment où il retrouve la nourriture ou il n'y a plus de possibilité.
        while(!finListNodeDijkstra){
            finListNodeDijkstra = true;
            for(NodeDijkstra n: listNodeDijstra){
                if(!n.getVisite()){
                    finListNodeDijkstra = false;
                    verifierDirection(n);
                    if (bloqueTrouve){
                        finListNodeDijkstra = false;
                    }
                    break;
                }
            }
        }

        //Maintenant il va chercher le NodeDijkstra qui est dans la place de la nourriture et va créer un chemin en appelant leurs parents.
        for(NodeDijkstra n: listNodeDijstra){
            if(n.getBloqueNourriture() ==true){
                while(n.getNodePere() != null){
                    listMouv.addFirst(n.getDirectionNode());
                    n = n.getNodePere();

                }
                break;
            }
        }
    }

    public void verifierDirection(NodeDijkstra nodeVisite){
        switch(nodeVisite.getDirectionNode()){
            case 'D':
                verifierLimites(nodeVisite, 'D');
                //La limitation dans les choix de direction permet que le snake retrouve toujours sa queue
                if(nodeVisite.getCorpSnake()[0][0]%2 == 0 ){
                    verifierLimites(nodeVisite, 'H');
                } else {
                    verifierLimites(nodeVisite, 'B');
                }
                nodeVisite.setVisite(true);
                break;
            case 'G':

                verifierLimites(nodeVisite, 'G');
                
                if(nodeVisite.getCorpSnake()[0][0]%2 == 0 ){
                    verifierLimites(nodeVisite, 'H');
                } else {
                    verifierLimites(nodeVisite, 'B');
                }
                nodeVisite.setVisite(true);
                break;
            case 'H':
                verifierLimites(nodeVisite, 'H');

                if(nodeVisite.getCorpSnake()[0][1]%2 == 0 ){
                    verifierLimites(nodeVisite, 'D');
                } else {
                    verifierLimites(nodeVisite, 'G');
                }
                nodeVisite.setVisite(true);
                break;
            case 'B':
                verifierLimites(nodeVisite, 'B'); 
                if(nodeVisite.getCorpSnake()[0][1]%2 == 0 ){
                    verifierLimites(nodeVisite, 'D');
                } else {
                    verifierLimites(nodeVisite, 'G');
                }
                nodeVisite.setVisite(true);
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
       
        //Elle vérifie les limites
        if(nodePere.getCorpSnake()[0][0] + nouvellePosition[0] > 19 || nodePere.getCorpSnake()[0][0]+nouvellePosition[0] < 0 || nodePere.getCorpSnake()[0][1] + nouvellePosition[1] > 13 ||
        nodePere.getCorpSnake()[0][1] + nouvellePosition[1] < 0){
            return false;
        }

         //Elle vérifie si le serpent ne touche pas sont corps
        for (int i=0; i<nodePere.getCorpSnake().length; i++){
            if(nodePere.getCorpSnake()[i][0] == nodePere.getCorpSnake()[0][0] + nouvellePosition[0] && nodePere.getCorpSnake()[i][1] == nodePere.getCorpSnake()[0][1] + nouvellePosition[1] ){
                return false;
            }
        }

         //Elle vérifie si le NodeDijkstra que l'on analyse n'est pas encore dans la listNodeDijkstra, sinon il est déjà visité.
        for(NodeDijkstra n: listNodeDijstra){
            if(n.getCorpSnake()[0][0] == nodePere.getCorpSnake()[0][0] + nouvellePosition[0] && n.getCorpSnake()[0][1] == nodePere.getCorpSnake()[0][1] + nouvellePosition[1]){
                return false;
            }
        }

        //Elle crée un nouveau NodeDijkstra
        listNodeDijstra.addLast(new NodeDijkstra(nodePere.getCorpSnake(), nodeFilsDirection, nouvellePosition, nodePere));

        //Elle vérifie si le NodeDijkstra que vient d'être créé n'est pas dans le même endroit que la nourriture. Si oui, il faut arrêter la recherche et déterminer le chemin à suivre
        if(listNodeDijstra.getLast().getCorpSnake()[0][0] == bloqueNourriture.getNourriture().getX() && listNodeDijstra.getLast().getCorpSnake()[0][1] == bloqueNourriture.getNourriture().getY()){
            listNodeDijstra.getLast().setBloqueNourriture(true);
            bloqueTrouve = true;
        }
        return true;
    }
}