package dev.moriaenergy;

import java.io.File;
import java.util.Scanner;

public class Parser {
    public static Plateau parse(String fichier){

        try{
            Scanner scan = new Scanner(new File("ressources/levels/" + fichier + ".nrg"));

            String str = scan.nextLine();
            Plateau plateau = convertTaille(str);


            int ligne = 0;
            while(scan.hasNextLine()){
             //   System.out.println("LIGNE : " + ligne);
                str = scan.nextLine();
                convertLigne(str, plateau, ligne);    
                ligne++;
            }
            if(!plateau.getS()){plateau.convertSquareToHexa();;}
            return plateau;
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    
    
    public static Plateau convertTaille(String taille){
        int w,h;  
        boolean S;
        String[] tab = taille.split(" ");
        w = Integer.parseInt(tab[0]);
        h = Integer.parseInt(tab[1]);
        if(tab[2].equals("S")){S = true;}else{S = false;}

        return new Plateau(S, w, h);
    }

    public static void convertLigne(String str,Plateau plateau,int index){
        String[] tab = str.split(" ");
        int compteur = 0;
        for(int i =0;i < tab.length;i++){
                switch (tab[i]) {
                    case ".":
                        //System.out.println("I : " + i + " . ");
                        plateau.array[index][compteur].x = ".";
                        compteur ++ ;
                        break;
                    case "S":
                        //System.out.println("I : " + i + " S ");
                        plateau.array[index][compteur].x = "S";
                        plateau.array[index][compteur].allumer = true;
                        plateau.listeAAllumer.add(plateau.array[index][compteur]);
                        compteur ++ ;
                        break;    
                    case "L":
                        //System.out.println("I : " + i + " L ");
                        plateau.array[index][compteur].x = "L";
                        plateau.listeAAllumer.add(plateau.array[index][compteur]);
                        compteur ++ ;    
                        break;
                    
                    case "W":
                        //System.out.println("I : " + i + " W ");
                        plateau.array[index][compteur].x = "W";
                        plateau.listeAAllumer.add(plateau.array[index][compteur]);
                        compteur ++ ;
                        break;

                    default:
                        if(testInteger(tab[i])){
                            compteur --;
                            int valeur = Integer.parseInt(tab[i]);
                            if(plateau.getS()){valeur = changeNumero(valeur);} // fais changement entre hexa et carre
                            plateau.array[index][compteur].rotation.add(valeur);
                            chercherVoisin(plateau,valeur, plateau.array[index][compteur]);
                            //test des differentes valeur;
                            compteur ++;
                        }
                        break;
                }
        }

        plateau.printArrayArray();
        System.out.println();
    }



    public static boolean testInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static int changeNumero(int valeur){
        if(valeur == 2){return 3;}else if(valeur == 3){return 5;}
        return valeur;
    }

    public static void chercherVoisin(Plateau plat,int valeur,Tuile tuile){

        int x = tuile.positionX;
        int y = tuile.positionY;

        System.out.println(x + " " + y + " " + tuile.x);
        switch(valeur){  
            case 0: 
                    tuile.voisins.add(plat.array[x-1][y]); //dessus
                    plat.array[x-1][y].voisins.add(tuile);
                    break;
            case 1:
                    //voisin droite
                    tuile.voisins.add(plat.array[x][y+1]);
                    plat.array[x][y+1].voisins.add(tuile);
                    break;
            case 2: 
                    //voisin en bas a droite
                    tuile.voisins.add(plat.array[x+1][y+1]);
                    plat.array[x+1][y+1].voisins.add(tuile);
                    break;
            case 3:
                    tuile.voisins.add(plat.array[x+1][y]); //dessous
                    plat.array[x+1][y].voisins.add(tuile);
                    break;

            case 4: 
                    tuile.voisins.add(plat.array[x+1][y-1]);  // en bas gauche = diagonal
                    plat.array[x+1][y-1].voisins.add(tuile);
                    break;

            case 5: 
                    // voisins gauche
                    tuile.voisins.add(plat.array[x][y-1]);
                    plat.array[x][y-1].voisins.add(tuile);

            default:
            break;
        }
    }


    public static void main(String[] args){
        //Plateau plat = convertTaille(test);
        Plateau plat = parse("level5");
        System.out.println();
        //convertSquareToHexa(plat);
        plat.printArrayArray();

        System.out.println(plat.debutParcour());

        for(int i =0;i<plat.listeAAllumer.size();i++){
            System.out.println(plat.listeAAllumer.get(i).x + " " + plat.listeAAllumer.get(i).allumer);
        }
    }
}
