/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Gerax
 */
public class VariableModel {
    HashMap<String, List<Object>> variable = new HashMap<>();
    
    public void createNewVariable(String name, List<Object> Value){
        variable.put(name, Value);
    }
    
    public void addTempValue(String name,String value){
        variable.get(name).add(value);
    }
    
    public boolean varibaleExist(String name){
        return variable.containsKey(name);
    }
    
    public Object lastValue(String key){
        return variable.get(key).get(variable.get(key).size()-1);
    }
    
    public void removeTempValue(String name){
        variable.get(name).remove(variable.size() -1);
    }
    
    public Object peekVariable(String key){
        return variable.get(key).get(variable.get(key).size());
    }
    
    public HashMap<String, List<Object>> getVariables(){
        return variable;
    }
    
}
