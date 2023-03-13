/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gerax
 */
public class modeloLisp {
    public List<Object> list = new ArrayList<>();
    List<String> comandos = Arrays.asList("quote","atom","eval","setq","defvar","cond","\'","list","defun");
    List<String> operators = Arrays.asList("+","-","/","*","^");
    List<String> conditionals = Arrays.asList("=","<",">","equal");
    
    public List<String> getCommand(){
        return comandos;
    }
    
    public List<String> getConditionals(){
        return conditionals;
    }
    
    public List<String> getOperators(){
        return operators;
    }
    
    public void addNewList(List<String> add){
        list.add(add);
        System.out.println(list);
    }
}
