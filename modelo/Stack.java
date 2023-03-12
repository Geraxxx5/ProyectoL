/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Gerax
 */
public class Stack<E> implements IStack<E> {

    ArrayList<E> stack;
    
    public Stack(){
        stack = new ArrayList<>();
    }
    
    @Override
    public void push(E item) {
        stack.add(item);
    }

    @Override
    public E pop() {
        return stack.remove(size()-1);
    }

    @Override
    public E peek() {
        return stack.get(size()-1);
    }

    @Override
    public boolean empty() {
        return (stack.size() == 0);
    }

    @Override
    public int size() {
        return stack.size();
    }
    
}
