package dev.moriaenergy;

import java.util.ArrayList;

public class Plateau {
    private final boolean S;
    private final int w, h;
    Tuile[][] array;

    ArrayList<Tuile> listeAAllumer;

    public Plateau(boolean S,int w,int h){ // Mettre les infos des levels dedans ou dans une class à part ? 
        this.S = S;
        this.h = h;
        this.w = w;
        this.listeAAllumer = new ArrayList<>();
        this.array = new Tuile[w][h];

        for(int i =0; i < array.length;i++){
            for(int j=0;j<array[i].length;j++){
                this.array[i][j] = new Tuile(null,i,j);
            }
        }

    }

    public boolean getS(){return this.S;}

    public int getW(){return this.w;}

    public int getH(){return this.h;}

    public void printArrayArray(){
        for(int i =0; i < this.array.length;i++){
            for(int j=0;j<this.array[i].length;j++){
                System.out.print(this.array[i][j].x + " ");
            }
            System.out.println();
        }
    }

    public void printVoisins(){
        for(int i =0; i < this.array.length;i++){
            for(int j = 0; j < this.array[i].length;j++){
                System.out.print(this.array[i][j].getVoisins() );
            }
            System.out.println();
        }
    }

    public  void convertSquareToHexa(){
        int index = this.array.length-1;
        for(int i =0; i < this.array[index].length;i++){
            if(i%2 == 1){
                this.array[index][i].x = null;
            }
        }
    }

    public void rotateTile(int x,int y){

        this.array[x][y].rotation(this.S);
        for(int i =0; i < this.array[x][y].rotation.size();i++){
            Parser.chercherVoisin(this, this.array[x][y].rotation.get(i), this.array[x][y]);    
        }
    }

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

}