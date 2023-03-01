package dev.moriaenergy;

import java.util.ArrayList;

public class Tuile {
    public String x;
    int positionX;
    int positionY;

    ArrayList<Tuile> voisins;

    public Tuile(String x,int positionX,int positionY){
        if(x == null){
            this.x = "V";
        }else {this.x = x;}
        
        this.positionX = positionX;
        this.positionY = positionY;
        voisins = new ArrayList<Tuile>();
    }

    public String getVoisins(){
        String str = "";
        for(int i = 0; i < this.voisins.size();i++){
            str += this.voisins.get(i).x + " ";
        }
        return str;
    }
}
