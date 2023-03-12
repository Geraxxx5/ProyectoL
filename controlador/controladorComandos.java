/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.modeloLisp;

/**
 *
 * @author Gerax
 */
public class controladorComandos {
    //controladorLisp cL = new controladorLisp();
    public controladorComandos(){
        
    }
    public String Quote(List<String> x){
        String quote = "";
        for(int i=0;i<x.size();i++){
            quote+=x.get(i);
        }
        return quote;
    }
    public boolean Atom(String x){
        if(x.length() > 1){
            return false;
        }else{
            return true;
        }
    }
    public boolean List(String x){
        return false;
    }
}
