package Modele;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Structures.FAPListe;
import Structures.Graphe;
import Structures.Sequence;
import Structures.Tuple;

class IASmart extends IA {

    @Override
	public Sequence<Coup> joue() {
        Sequence<Coup> res = Configuration.nouvelleSequence();
        //a implementer
        return res;
    }

    @Override
    public void testdep(int l, int c){
        int [][][] resP = Aetoiles(niveau.pousseurL, niveau.pousseurC, l, c);
        Tuple<Integer> arriver = new Tuple<Integer>(l, c);
        Sequence<Coup> resC = creeCoup(resP,arriver);
        ControleurMediateur jouerlejeu = new ControleurMediateur(this.jeu);
        while (!resC.estVide()){
            Coup tmp = resC.extraitTete();
            System.out.println(tmp.dirPousseurL + " " + tmp.dirPousseurC);
            jouerlejeu.joue(tmp);
        }
    }

    public Sequence<Coup> creeCoup(int[][][] chemain, Tuple<Integer> arriver){
        Sequence<Coup> res = Configuration.nouvelleSequence();
        int dL=arriver.sortF();
        int dC=arriver.sortS();
        int tmpL,tmpC;
        while(chemain[dL][dC][1]!=-1){
            Coup coups = new Coup();
            tmpL=chemain[dL][dC][1];
            tmpC=chemain[dL][dC][2];
            coups.deplacementPousseur(tmpL,tmpC,dL,dC);
            res.insereTete(coups);
            dL=tmpL;
            dC=tmpC;
        }
        return res;
    }

    //fonction qui teste si dans un tableau de booleen il y a une valeur true
    public boolean tableauVide(boolean[][] T, int c, int l){
        for (int a=0; a<l ; a++){
            for(int b=0; b<c ; b++){
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

        while(!tableauVide(explorer, nbr_c, nbr_l)){
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
            if(min_c+1<nbr_c){
                if(niveau.estVide(min_l, min_c+1) && ((distance[min_l][min_c+1][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l][min_c+1][0]))){
                    distance[min_l][min_c+1][0]=distance[min_l][min_c][0]+1;    //incremente la distance du nv sommet
                    //ont garde en memoire d'ou ont vien
                    distance[min_l][min_c+1][1]=min_l;  
                    distance[min_l][min_c+1][2]=min_c;
                    explorer[min_l][min_c+1]=true;      //ont ajoute le sommet au sommet atraiter
                }
            }
            if(min_c-1>0){
                if(niveau.estVide(min_l, min_c-1) && ((distance[min_l][min_c-1][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l][min_c-1][0]))){
                    distance[min_l][min_c-1][0]=distance[min_l][min_c][0]+1;
                    distance[min_l][min_c-1][1]=min_l;
                    distance[min_l][min_c-1][2]=min_c;
                    explorer[min_l][min_c-1]=true;
                }
            }
            if(min_l+1<nbr_l){
                if(niveau.estVide(min_l+1, min_c) && ((distance[min_l+1][min_c][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l+1][min_c][0]))){
                    distance[min_l+1][min_c][0]=distance[min_l][min_c][0]+1;
                    distance[min_l+1][min_c][1]=min_l;
                    distance[min_l+1][min_c][2]=min_c;
                    explorer[min_l+1][min_c]=true;
                }
            }
            if(nbr_l-1>0){
                if(niveau.estVide(min_l-1, min_c) && ((distance[min_l-1][min_c][0]==-1) || ((distance[min_l][min_c][0]+1)<distance[min_l-1][min_c][0]))){
                    distance[min_l-1][min_c][0]=distance[min_l][min_c][0]+1;
                    distance[min_l-1][min_c][1]=min_l;
                    distance[min_l-1][min_c][2]=min_c;
                    explorer[min_l-1][min_c]=true;
                }
            }
        }
        //renvois null si pas de chemain trouver
        return null;
    }

    int calculeH(Graphe<Tuple<Integer>> G){
        //ici calculer la distance entre chaque caise (contenue dans element de g) et le but le plus proche
        //additionner tout sa 
        return 1;
    }

    boolean peutPouser(int l , int c){
        return (!niveau.aCaisse(l, c) && !niveau.aMur(l, c));
    }

    //renvois le sommet si il existe si non renvois null
    Graphe<Tuple<Integer>> existe(Sequence<Graphe<Tuple<Integer>>> sommetcree , Graphe<Tuple<Integer>> existe){
        Sequence<Graphe<Tuple<Integer>>> copisommetcree = sommetcree;
        Graphe<Tuple<Integer>> tmp =copisommetcree.extraitTete();
        sommetcree.extraitTete();
        if(tmp.compareTo(existe)==0){
            return tmp;
        }
        sommetcree.insereQueue(tmp);
        while(!sommetcree.estVide()){
            tmp =copisommetcree.extraitTete();
            sommetcree.extraitTete();
            if(tmp.compareTo(existe)==0){
                return tmp;
            }
            sommetcree.insereQueue(tmp);
        }
        return null;
    }

    public Sequence<Graphe<Tuple<Integer>>> AetoilesCasee(Graphe<Tuple<Integer>> G, Graphe<Tuple<Integer>> fin){
        Graphe<Tuple<Integer>> min,tmp; //variable utiliser pour trouver le minimum dans traiter
        Sequence<Graphe<Tuple<Integer>>> traiter = Configuration.nouvelleSequence();    // les sommet a traiter
        Sequence<Graphe<Tuple<Integer>>> sommetcree = Configuration.nouvelleSequence() ; // repertori les sommet deja cree
        //ont insert le premier sommet et ont fixe sa taille a 0
        G.distance = 0;
        traiter.insereQueue(G);
        //boucle de A*
        while(!traiter.estVide()){
            //calcule du du sommet de traiter le plus petit
            min=traiter.extraitTete();
            Sequence<Graphe<Tuple<Integer>>> tmptable = Configuration.nouvelleSequence();
            while(!traiter.estVide()){
                tmp = traiter.extraitTete();
                if (calculeH(min)>calculeH(tmp)){
                    tmptable.insereQueue(min);
                    min=tmp;
                }
                else{
                    tmptable.insereQueue(tmp);
                }
            }
            traiter=tmptable;
            //le sommet a traiter est dans min

            FAPListe<Tuple<Integer>> copimin = min.sortSommet();
            FAPListe<Tuple<Integer>> copifin = fin.sortSommet();
            boolean egal=true;
            while (!copimin.estVide() && egal){
                if(copimin.extrait().compareTo(copifin.extrait())!=0){
                    egal=false;
                }
            }
            if(egal){
                sommetcree.insereTete(min);;
                return sommetcree;
            }

            //ont calcule pour ces voisin 
            copimin=min.sortSommet();
            while(!copimin.estVide()){
                Tuple<Integer> voisin = copimin.extrait();
                int [][][] resAstarstmp;
                if(peutPouser(voisin.sortF()+1, voisin.sortS()) && ((resAstarstmp=Aetoiles(min.sortElement().sortF(), min.sortElement().sortF(),voisin.sortF()-1, voisin.sortS()))!=null)){
                    int distancetmp = resAstarstmp[voisin.sortF()-1][voisin.sortS()][0];
                    Tuple<Integer> nvposjoueur = new Tuple<Integer>(voisin.sortF()-1, voisin.sortS());
                    Graphe<Tuple<Integer>> straiter = new Graphe<Tuple<Integer>>(nvposjoueur);
                    //ici lui ajouter les sommet puis tester si ont a deja le sommet , continuer algo A*
                    //ajout des somet en changent la case traiter
                    copifin=min.sortSommet();
                    while(!copifin.estVide()){
                        Tuple<Integer> inserttmp = copifin.extrait();
                        if(inserttmp.compareTo(voisin)==0){
                            inserttmp.first=inserttmp.first+1;
                            straiter.ajoutS(inserttmp);
                        }
                        else{
                            straiter.ajoutS(inserttmp);
                        }
                    }

                    //ont teste si la nouvelle distance est mieux si oui ont le change et ont ajoute le somet aux sommet atraiter
                    Graphe<Tuple<Integer>> graphetmp;
                    if((graphetmp=existe(sommetcree, straiter))!=null){
                        if(min.distance+distancetmp<graphetmp.distance){
                            graphetmp.distance=min.distance+distancetmp;
                            traiter.insereQueue(graphetmp);
                        }
                        else{
                            sommetcree.insereQueue(straiter);
                            straiter.distance=min.distance+distancetmp;
                            traiter.insereQueue(straiter);
                        }
                    }
                }
            }

        }
        return null;

    }
    
}
