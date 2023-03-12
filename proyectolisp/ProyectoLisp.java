package proyectolisp;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import controlador.CreateList;
import controlador.controladorComandos;
import controlador.controladorLisp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import modelo.modeloLisp;

/**
 *
 * @author Gerax
 */
public class ProyectoLisp {

    public static void main(String[] args) {
        modeloLisp mL = new modeloLisp();
        controladorLisp cL = new controladorLisp(mL);

        //System.out.println(CreateList.parse(Resultado().toLowerCase()));
            List<Object> list = CreateList.parse(Resultado().toLowerCase());
            System.out.println(list);
            System.out.println(cL.evaluate(list));
        //controladorComandos cM = new controladorComandos();
        //List<String> list = cL.intento(Resultado());
        
        /*DoublyLinkedList<String> list = new DoublyLinkedList<>();
        //String m = "(defun mi-menor (x y) (cond ((< x y) x) (t y)))(mi-menor 5 1)";
        String m = "Quote jajaja";
        
        System.out.println(m);
        List<String> bobo = new ArrayList<String>();
        bobo = cL.intento(m);*/
    }
    //String[]
    public static String Resultado(){
        FileReader archivo;
        BufferedReader lector;
        try {
            archivo = new FileReader("datos.txt"); // modificar del archivo querido
            if (archivo.ready()) {
                    lector = new BufferedReader(archivo); // se hace la lectura
                    String cadena;
                    String[] primerCorte = null;
                    String[] resultado = null;
                    String algo = "";
                    String devolver = "";
                    while ((cadena = lector.readLine()) != null) {
                            /*primerCorte = cadena.split(" ");
                            for(String corte:primerCorte){
                                if(corte.length() > 1){
                                    for(int i = 0;i<corte.length();i++){
                                        devolver+= corte.charAt(i)+" ";
                                    }
                                }else{
                                    devolver+= corte+" ";
                                }
                            }
                            resultado = devolver.split(" ");*/
                            devolver+=cadena;
                            //System.out.println("Operacion: " + cadena); // se agrega la operacion leida
                            //System.out.println("Resultado: " + calculadora.evaluate(traslater.convertidor(cadena))); // se llama a
                                                                                                                                                                                                            // la
                                                                                                                                                                                                            // calculadora
                                                                                                                                                                                                            // para el
                                                                                                                                                                                                            // metodo
                                                                                                                                                                                                            // evaluate
                            System.out.println("-----------------------------------------------");
                            // Mandaremos a la cadena.
                    }
                    resultado = devolver.split(" ");
                    //return resultado;
                    String retorno = "";
                    for(String individual: resultado){
                        if(individual.length() != 0){
                            retorno+=individual+" ";
                        }
                    }
                    String[] listaR = retorno.split(" ");
                    return devolver;
            } else {
                    System.out.println("El archivo no se encuentra");
            }
            
        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
}
