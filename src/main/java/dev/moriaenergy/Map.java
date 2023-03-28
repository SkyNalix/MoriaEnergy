package dev.moriaenergy;

import java.util.ArrayList;

public class Map {
    private final int H, W;
    final Cell[][] array;

    final ArrayList<Cell> listeAAllumer;

    public Map( int H, int W  ){ // Mettre les infos des levels dedans ou dans une class à part ?
        this.H = H;
        this.W = W;
        this.listeAAllumer = new ArrayList<>();
        this.array = new Cell[H][W];
    }

    public int getW(){return this.W;}

    public int getH(){return this.H;}

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < getW() && y >= 0 && y < getH();
    }

/*    public void rotateTile(int x,int y){
        this.array[x][y].rotation(this.S);
        for(int i =0; i < this.array[x][y].rotation.size();i++){
            Parser.chercherVoisin(this, this.array[x][y].rotation.get(i), this.array[x][y]);
        }
    }
*/

    /*
    public boolean debutParcour(){ // on parcours une fois depuis la source puis à partir des wifis allumé

        //parcours jusqu'à trouver une source
        for(int i=0;i<this.array.length;i++){
            for(int j=0;j<this.array[i].length;j++){
                Tuile tuile = this.array[i][j];
                if(!(tuile.x == null) && tuile.x.equals("S")){
                    parcourVoisin(tuile);
                }
            }
        }

        //parcours des wifis
        for(int i=0;i<this.array.length;i++){
            for(int j=0;j<this.array[i].length;j++){
                Tuile tuile = this.array[i][j];
                if(!(tuile.x==null) && tuile.x.equals("W")){
                    //parcours des voisins à partir d'ici
                    parcourVoisin(tuile);
                }
            }
        }

        // vérification que tout est allumé
        for(int i=0;i<this.listeAAllumer.size();i++){
            if(!this.listeAAllumer.get(i).allumer) return false;
        }
        return true;
    }

    public void parcourVoisin(Tuile tuile){
        tuile.allumer = true;
        for(int i=0;i<tuile.voisins.size();i++){
            Tuile voisin = tuile.voisins.get(i);
            if(!(voisin.x == null) && !voisin.allumer && voisin.x.equals("W"))
                allumerWifi();

            if(!(voisin.x == null) && !voisin.allumer && !voisin.x.equals("W"))
               parcourVoisin(tuile.voisins.get(i));
        }
    }

    public void allumerWifi(){ // ou alors on crée des connexions entre chaque wi-fi ??
        for(int i=0;i<this.listeAAllumer.size();i++){

            if( !(this.listeAAllumer.get(i).x == null) && this.listeAAllumer.get(i).x.equals("W")) this.listeAAllumer.get(i).allumer = true;
        }
    }
    */

}