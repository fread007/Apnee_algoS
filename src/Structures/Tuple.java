package Structures;

public class Tuple<E extends Comparable<E>> implements Comparable<Tuple<E>> {
    public E first;
    public E seconde;

    public Tuple(E a, E b){
        first = a;
        seconde = b;
    }

    public E sortF(){
        return first;
    }

    public E sortS(){
        return seconde;
    }

    //pour comparer 2 tuple ont definit que le 1er element a plus d'importence que le 2eme
    //ont regarde donc le desieme seulement si les 1er sont egaux
    @Override
    public int compareTo(Tuple<E> b){
        if (this.sortF().compareTo(b.sortF())==0){ 
            return this.sortS().compareTo(b.sortS());
        }
        else{
            return this.sortF().compareTo(b.sortF());
        }
    }
}
