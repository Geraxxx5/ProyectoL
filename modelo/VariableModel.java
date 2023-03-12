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
    HashMap<String, List<String>> variable = new HashMap<>();
    
    public void createNewVariable(String name, List<String> Value){
        variable.put(name, Value);
    }
    
    public void addTempValue(String name,String value){
        variable.get(name).add(value);
    }
    
    public boolean varibaleExist(String name){
        return variable.containsKey(name);
    }
    
    public String lastValue(String key){
        return variable.get(key).get(variable.get(key).size()-1);
    }
    
    public void removeTempValue(String name){
        variable.get(name).remove(1);
    }
    
    public HashMap<String, List<String>> getVariables(){
        return variable;
    }
    
}
