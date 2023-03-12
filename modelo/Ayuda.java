package modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Ayuda {
    //1 lista
    //2 int
    //3 String
    int valueToGo = 0;
    int valueInt = 0;
    String value;
    List<Ayuda> list = new ArrayList<Ayuda>();
    
    public List<Ayuda> getList(){
        return list;
    }
    
    public String getValue(){
        return value;
    }
    
    public Ayuda(int x){
        this.valueToGo = x;
    }
    
    public int getValueToGO(){
        return this.valueToGo;
    }
    
    public void addList(Ayuda x){
        list.add(x);
    }
    
    public void setValueToGO(){
        this.valueToGo = valueToGo;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public void setList(List<Ayuda> list){
        this.list = list;
    }
}
