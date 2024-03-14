package FONTS.src;
import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private final int size;
    private final HashSet<TypeOperation> operations;
    private final ArrayList<KenkenCage> cages;
    private final TypeDificult dificult;
    private String name;


    Kenken(){
        this.dificult = TypeDificult.MEDIUM;
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

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult, String name){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
        this.name = name;
    }
    public void generate() {
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {

            }
        }
    }

    /*public boolean isValid() {

    }*/

//    public boolean rowCheck() {
//
//    }

//    public boolean columnCheck() {
//
//    }
}
