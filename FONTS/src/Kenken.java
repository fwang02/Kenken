package FONTS.src;
import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private int size;
    private HashSet<TypeOperation> operations;
    private ArrayList<KenkenCage> cages;
    public TypeDificult dificult;

    Kenken(){
        size = 0;
        cages = new ArrayList<KenkenCage>();
        operations = new HashSet<TypeOperation>();
    }

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
    }
    public void generate() {

    }

}
