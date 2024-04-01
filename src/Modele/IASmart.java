package Modele;

import Global.Configuration;
import Structures.Sequence;

class IASmart extends IA {

    @Override
	public Sequence<Coup> joue() {
        Sequence<Coup> res = Configuration.nouvelleSequence();
        //a implementer
        return res;
    }

    //fonction qui teste si dans un tableau de booleen il y a une valeur true
    public boolean tableauVide(boolean[][] T, int c, int l){
        for (int a=0; a<c ; a++){
            for(int b=0; a<l ; a++){
                if(T[a][b]==true){
                    return false;
                }
            }
        }
        return true;
    }

    
    

    //ont cherche le plus cours chemain entre (i,j) et (k,l)
    public int[][][] Aetoiles(int i , int j , int k , int l){
        int nbr_c = niveau.c;
        int nbr_l = niveau.l;
        int[][][] distance = new int[nbr_l][nbr_c][3] ;    //tableau ou l'on stoque la distance entre la case (k,l) et la case de depart, stoque le somet dorigine
        boolean[][] explorer = new boolean[nbr_l][nbr_c] ;    //tableau des case a explore

        //ont initialise toute les valeur a -1 et a false
        for (int a=0; a<nbr_l ; a++){
            for(int b=0; b<nbr_c ; b++){
                distance[a][b][0]=-1;
                distance[a][b][1]=-1;
                distance[a][b][2]=-1;
                explorer[a][b]=false;
            }
        }
        //ont commence par la case du debut
        explorer[i][j]=true;
        distance[i][j][0]=0;

        int min=-1;
        int min_c=0, min_l=0;
        int calculeH;

        while(tableauVide(explorer, nbr_c, nbr_l)){
            //calcule le prochain somet a traiter en prenant le plus cours
            min=-1;
            for (int a=0; a<nbr_l ; a++){
                for(int b=0; b<nbr_c ; b++){
                    if(explorer[a][b]==true){   //teste si il est a explorer
                        calculeH = distance[a][b][0]+(Math.abs(a-k)+Math.abs(b-l));
                        if((min==-1) || (calculeH<min)){
                            min=calculeH;
                            min_c=b;
                            min_l=a;
                        }
                    }
                }
            }

            //teste si ont a trouver le chemain
            if((min_c==l)&&(min_l==k)){
                return distance;
            }


            //si non ont enleve le somet
            explorer[min_l][min_c]=false;

            //et ont ajoute ces voisins
            if(niveau.estVide(min_l+1, min_c) && ((distance[min_l][min_c+1][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l][min_c+1][0]))){
                distance[min_l][min_c+1][0]=distance[min_l][min_c][0]+1;    //incremente la distance du nv sommet
                //ont garde en memoire d'ou ont vien
                distance[min_l][min_c+1][1]=min_l;  
                distance[min_l][min_c+1][2]=min_c;
                explorer[min_l][min_c+1]=true;      //ont ajoute le sommet au sommet atraiter
            }
            if(niveau.estVide(min_l-1, min_c) && ((distance[min_l][min_c-1][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l][min_c-1][0]))){
                distance[min_l][min_c-1][0]=distance[min_c][min_l][0]+1;
                distance[min_l][min_c-1][1]=min_l;
                distance[min_l][min_c-1][2]=min_c;
                explorer[min_l][min_c-1]=true;
            }
            if(niveau.estVide(min_l, min_c+1) && ((distance[min_l+1][min_c][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l+1][min_c][0]))){
                distance[min_l+1][min_c][0]=distance[min_c][min_l][0]+1;
                distance[min_l+1][min_c][1]=min_l;
                distance[min_l+1][min_c][2]=min_c;
                explorer[min_l+1][min_c]=true;
            }
            if(niveau.estVide(min_l, min_c-1) && ((distance[min_l-1][min_c][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l-1][min_c][0]))){
                distance[min_l-1][min_c][0]=distance[min_c][min_l][0]+1;
                distance[min_l-1][min_c][1]=min_l;
                distance[min_l-1][min_c][2]=min_c;
                explorer[min_l-1][min_c]=true;
            }
        }

        return distance;
                

    }
    
}