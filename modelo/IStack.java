/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Gerax
 */
public interface IStack<E> {
    public void push(E item);
   // post: el item es agregado al stack. Sera
   //       el proximo en salir

   public E pop();
   // pre: stack no esta vacio
   // post: el elemento ingresado mas recientemente
   //       es retirado.

   public E peek();
   //pre: stack no esta vacio
   //post: el valor mas reciente es retornado
   //      pero no es sacado del stack.

   public boolean empty();
   //post: regresa true si el stack esta vacio

   public int size();
   //post: regresa la cantidad de elementos
   //      en el stack
}
