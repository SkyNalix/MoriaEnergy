package dev.moriaenergy;

import java.util.ArrayList;
import java.util.List;


public class Map {
    private final int H, W;
    public final Cell[][] array;

    public Map( int H, int W  ){ // Mettre les infos des levels dedans ou dans une class à part ?
        this.H = H;
        this.W = W;
        this.array = new Cell[H][W];
    }

    public void updateWifi() {
        boolean enabled = false;
        for(Cell[] cells : array)
            for(Cell cell : cells)
                if( cell.tile == Tile.W && cell.seekPower( this ) ) {
                    enabled = true;
                    break;
                }
        for(Cell[] cells : array)
            for(Cell cell : cells)
                if(cell.tile == Tile.W)
                    cell.setEnabled( this, enabled );
    }

    public int getW(){return this.W;}

    public int getH(){return this.H;}

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < getW() && y >= 0 && y < getH();
    }

    public boolean victory(){
        for(int i = 0;i < this.array.length;i++){ //on regarde si toutes les lampes sont allumés
            for(int j=0;j < this.array[i].length;j++){
                Cell tuile = this.array[i][j];
                if(tuile.tile != null ){
                    if(tuile.tile == Tile.L){
                        if(!tuile.isEnabled()){ return false; }
                    }
                }
            }
        }
        return true;
    }

    void victory2(Cell cell){
        List<Integer> before_rotation = new ArrayList<>( cell.rotations );

		cell.rotate();

		// on regarde ces ancients voisins, et on desactive si ils ne sont
		// plus alimentées
		for(Cell neighbor : cell.getNeighbors(this, before_rotation)) {
			if( !neighbor.seekPower( this ) ) {
				neighbor.setEnabled( this, false );
			}
		}

		// on regarde si un des ces nouveaux voisins est alimenté
		boolean found = false;
		for(Cell neighbors : cell.getNeighbors(this)) {
			if( neighbors.isEnabled() ) {
				found = true;
				break;
			}
		}
		cell.setEnabled( this, found );
		cell.update_rotations_images();
		this.updateWifi();
    }

    /* prendre en input une Map non tourné 
     * pour une map on tourne chaque cellule  jusqu'à avoir une combinaison gagnante
     */
    public boolean multipleVictory(){
        System.out.println("-----------DEBUT---------------");

        ArrayList<ArrayList<List<Integer>>> listeCopieVictoire = new ArrayList<>();
        int echantillon = 0;

        for(int x = 0;x<this.array[0][0].getMaxNeighbors();x++){
            for(int i =0; i < this.array.length;i++){
                for(int j =0;j< this.array[i].length;j++){
                    Cell cell = this.array[i][j];
                    for(int k=0; k< cell.getMaxNeighbors();k++){
                        //cell.rotate();

                        victory2(cell);
                        
                        if(this.victory()){
                            ArrayList<List<Integer>> test = copieVictoire();
                            if(echantillon < 10){
                                //printVictoire(test);
                                echantillon ++;
                            }
                            

                            if(rip(listeCopieVictoire, test) == false){ listeCopieVictoire.add(test);}
                        }
                            
                    }
                    //cell.rotate();
                    //victory2(cell);
                }
            }
        }
        //this.printMap();
        

        for(int i=0;i<listeCopieVictoire.size();i++){
            printVictoire(listeCopieVictoire.get(i));
        }


        System.out.println( "/\\" + listeCopieVictoire.size());
        return listeCopieVictoire.size() >1 ;
    }

    boolean rip(ArrayList<ArrayList<List<Integer>>> listeCopieVictoire,ArrayList<List<Integer>> test){
        for(int i =0;i< listeCopieVictoire.size();i++){
            if(  listeCopieVictoire.get(i).equals(test) ){
                return true;
            }
        }

        return false;
    }

    ArrayList<List<Integer>> copieVictoire(){ // une configuration de victoire
        ArrayList<List<Integer>> result = new ArrayList<>();

        for(Cell[] ligne : this.array){
            for(Cell cell : ligne){;
                List<Integer> x = new ArrayList<>();
                for(int i=0;i<cell.rotations.size();i++){
                    x.add(cell.rotations.get(i));
                }
                result.add(x);
            }
        }

        return result;
    }

    

    void printMap(){
        for(int i =0;i< this.array.length;i++){
            for(int j=0;j<this.array[i].length;j++){
                System.out.print(this.array[i][j].tile + ": ");
                for(int k=0;k<this.array[i][j].rotations.size();k++){
                    System.out.print(this.array[i][j].rotations.get(k) + ",");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    void printVictoire(ArrayList<List<Integer>> map1){
            for(int j=0;j <map1.size();j++){
                for(Integer l : map1.get(j)){
                    System.out.print(l + ",");
                }
                System.out.println();
            }
            System.out.println("-------------------------------"); 
    }

    
}