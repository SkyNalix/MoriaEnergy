package dev.moriaenergy;

public class Plateau {
    private boolean S;
    private int w,h;
    Tuile[][] array;

    public Plateau(boolean S,int w,int h){ // Mettre les infos des levels dedans ou dans une class à part ? 
        this.S = S;
        this.h = h;
        this.w = w;

        

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


    public void main(String[] args){
        
    }
}