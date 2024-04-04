package Structures;

import Structures.*;

public class Graphe<E extends Comparable<E>> implements Comparable<Graphe<E>> {
    FAPListe<E> sommet;
    E element;
    public int distance;

    public Graphe(E a){
        sommet = new FAPListe<E>();
        element = a;
        distance = -1;
    }

    public FAPListe<E> sortSommet(){
        return sommet;
    }

    public E sortElement(){
        return element;
    }

    public int sortDistance(){
        return distance;
    }
    
    public void ajoutS(E a){
        sommet.insere(a);
    }

    public E sortS(){
        return sommet.extrait();
    }

    public boolean sommetVide(){
        return sommet.estVide();
    }

    public int compareTo(Graphe<E> A){
        int res;
        if ((res=this.element.compareTo(A.element))!=0){
            return res;
        }
        else{
            FAPListe<E> tmpG = sommet;
            FAPListe<E> tmpA = A.sortSommet();
            while (!tmpG.estVide()){
                if((res=tmpG.extrait().compareTo(tmpA.extrait()))!=0){
                    return res;
                }
            }
            return 0;
        }
    }

}
