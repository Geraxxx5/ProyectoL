package controlador;

import java.util.ArrayList;
import java.util.List;

public class controladorLista {
    int count = 0;
    List<String> list = new ArrayList<>();
    
    public void createNewList(String item){
        list.add(item);
        count++;
    }
    
    
    public String accesValueLastNode(){
        return null;
    }
    
    public String lastValue(){
        count--;
        return null;
    }
    
    public int size(){
        return count;
    }
}
