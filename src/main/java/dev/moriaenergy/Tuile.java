package dev.moriaenergy;

import java.util.ArrayList;

public class Tuile {
    public String x;
    int positionX;
    int positionY;
    ArrayList<Integer> rotation;
    ArrayList<Tuile> voisins;
    boolean allumer ;

    public Tuile(String x,int positionX,int positionY){
        if(x == null){
            this.x = "V";
        }else {this.x = x;}
        
        this.allumer = false;
        this.positionX = positionX;
        this.positionY = positionY;
        rotation = new ArrayList<Integer>();
        voisins = new ArrayList<Tuile>();
    }

    public String getVoisins(){
        String str = "";
        for(int i = 0; i < this.voisins.size();i++){
            str += this.voisins.get(i).x + " ";
        }
        return str;
    }

    public void rotation(boolean s){
        for(int i=0; i<this.rotation.size();i++ ){
            
            int valeur = this.rotation.get(i);
            int modulo = 4;
            if(!s) modulo = 6;

            valeur = (valeur + 1) % modulo;
            this.rotation.set(i,valeur);
        }
    }


    /*public void parcoursVoisin(){ // part du principe qu'on vient d'un endroit allumer
            this.allumer = true;
            for(int i=0;i<this.voisins.size();i++){
                this.voisins.get(i).parcoursVoisin();
            }
    } */

}
