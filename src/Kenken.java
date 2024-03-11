package src;
import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private int size;
    private HashSet<Character> operations;
    private ArrayList<KenkenCage> cages;
    public TypeDificult dificult;

    Kenken(){
        size = 0;

    }

    Kenken(int size, HashSet<Character> operations, TypeDificult dificult){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;

    }

}
