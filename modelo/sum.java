/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author Gerax
 */
public class sum implements Function{

    @Override
    public Object apply(List<Object> args) {
        int sum = 0;
        for (Object arg : args) {
            sum += (int) arg;
        }
        return sum;
    }
    
}
