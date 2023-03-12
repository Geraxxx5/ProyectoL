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
public class modelVariables {
    HashMap<String, List<String>> variables = new HashMap<>();
    //key: name, Value: List<Params>, rest
    HashMap<String, List<Object>> function = new HashMap<>();
}
