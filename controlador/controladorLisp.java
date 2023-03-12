package controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import modelo.Ayuda;
import modelo.Function;
import modelo.Stack;
import modelo.VariableModel;
import modelo.modeloLisp;
import modelo.sum;

public class controladorLisp {
    
    Stack<String> stack;
    modeloLisp lispModel = new modeloLisp();
    controladorComandos commandControl = new controladorComandos();
    List<List<String>> list = new ArrayList<>();
    VariableModel variable = new VariableModel();
    
    public controladorLisp(modeloLisp lispModel){
        this.lispModel = lispModel;
        stack = new Stack<>();
    }
    
    public int precedence(String character){
        if(character.equals("-") || character.equals("+")){
            return 1;
        }else if(character.equals("*") || character.equals("/")){
            return 2;
        }else if(character.equals("^")){
            return 3;
        }else{
            return 0;
        }
    }
    
    public double calcular(String[] expression) throws ArithmeticException{
        Stack<Double> numbers = new Stack<>();
        for(String ch: expression){
            char isNumber = ch.charAt(0);
            if(Character.isDigit(isNumber)){
                numbers.push(Double.parseDouble(ch));
            }else{
                double operadorB = numbers.pop();
                double operadorA = numbers.pop();
                switch(ch){
                    case"+":
                        numbers.push(operadorA + operadorB);
                        break;
                    case "-":
                        numbers.push(operadorA - operadorB);
                        break;
                    case"*":
                        numbers.push(operadorA * operadorB);
                        break;
                    case"/":
                        if(operadorB == 0.00){
                            throw new ArithmeticException("Se intento dividir entre 0"); 
                        }else{
                            numbers.push(operadorA / operadorB);
                        }
                        break;
                    case"^":
                        numbers.push(Math.pow(operadorA, operadorB));
                        break;
                }
            }
        }
        return numbers.pop();
    }
    
    public String[] leer(String[] prefix){
        String post = "";
        stack.push("#");
        for(int i = 0;i<prefix.length;i++){
            String character = prefix[i];
            if(!character.equals(" ")){
                char isNumber = character.charAt(0);
                if(Character.isDigit(isNumber)){
                    post+= character+" ";
                }else if(character.equals("(")){
                    stack.push("(");
                }else if(character.equals("^")){
                    stack.push("^");
                }else if(character.equals(")")){
                    if(!stack.empty() && !(stack.peek().equals("("))){
                        post+= stack.pop()+" ";
                    }
                    stack.pop();
                }else{
                    if(precedence(character) > precedence(stack.peek())){
                        stack.push(character);
                    }else{
                        while(!stack.peek().equals("#") && (precedence(character) <= precedence(stack.peek()))){
                            post += stack.pop();
                        }
                        stack.push(character);
                    }
                }
            }    
        }
        String[] res = post.split(" ");
        return res;
    }
    
    public boolean checkBracket(String toCheck){
        int open = 0;
        int close = 0;
        for(int i = 0; i<toCheck.length();i++){
            char character = toCheck.charAt(i);
            if(character == '('){
                open++;
            }
            if(character == ')'){
                close++;
            }
        }
        boolean same = false;
        if(open == close){
            same = true;
        }
        return same;
    }
    
    public String[] split(String exp) {
        if (exp.charAt(0) != '(') {
            return new String[] {exp};
        }
        String[] tokens = exp.substring(1, exp.length() - 1).split(" ");
        List<String> output = new ArrayList<>();
        int p = 0;
        List<String> temp = new ArrayList<String>();
        for (String t : tokens) {
            if (t.startsWith("(")) {
                p++;
            } else if (t.endsWith(")")) {
                p--;
            }
            temp.add(t);
            if (p == 0) {
                output.add(String.join(" ", temp));
                temp = new ArrayList<>();
            }
        }
        return output.toArray(new String[0]);
    }
    
    public List<String> separeteBrackets(String x){
        List<String> intento = new ArrayList<>();
        if(x.charAt(0) == '('){
            x = x.substring(1, x.length()-1);
        }
        int start = 0;
        while(start < x.length()){
            int endIndex = next(x, start);
            intento.add(x.substring(start, endIndex));
            start = endIndex + 1;
        }
        System.out.println(intento);
        return intento;
    }
    
    public int next(String x, int start){
        int index = start;
        if(x.charAt(index) == '('){
            int count = 1;
            index++;
            while(index < x.length() && count>0){
                if(x.charAt(index) == '('){
                    count++;
                }else if(x.charAt(index)==')'){
                    count--;
                }
                index++;
            }
        }else{
            while(index < x.length() && x.charAt(index) != ' '){
                index++;
            }
        }
        return index;
    }
    
    public List<String> hacerLista(String what){
        what = what.replace("(", " ( ").replace(")", " ) ").trim();
        return Arrays.asList(what.split(" "));
    }
    
    public String doList(String x){
        if(checkBracket(x)){
            List<String> list = separeteBrackets(x.trim().toLowerCase());
            boolean finish = true;
            int count = 1;
            int index = 0;
            while(finish){
                lispModel.addNewList(separeteBrackets(list.get(index)));
                if(count < list.size()){
                    count++;
                    index++;
                }else{
                    finish = false;
                }
            }
        }
        return null;
    }
    
    public Object evaluate(List<Object> expr){
            Object first = expr.get(0);
            System.out.println("first: "+first);
            if (first instanceof List || ((first instanceof String)&& (expr instanceof List))) {
                List<Object> listaExpresion;
                if(first instanceof String){
                    listaExpresion = (List<Object>) expr;
                }else{
                    listaExpresion = (List<Object>) first;
                }
                System.out.println(listaExpresion);
                String oper = (String) listaExpresion.get(0);
                System.out.println(oper);
                if(lispModel.getCommand().contains(oper)){
                    if(oper.equals("quote")){
                        return listaExpresion.get(1);
                    }
                    else if(oper.equals("eval")){
                        Object second = listaExpresion.get(1);
                        if(second instanceof List){
                            List<Object> secondValue = (List<Object>) second;
                            Object response = evaluate(secondValue);;
                            if(response instanceof List){
                                List<Object> toEvaluate = (List<Object>) response;
                                return evaluate(toEvaluate);
                            }else{
                                return response;
                            }
                        }else{
                            return second;
                        }
                    }else if(oper.equals("setq") || oper.equals("defvar")){
                        if(listaExpresion.get(1) instanceof String){
                            String variableName = (String) listaExpresion.get(1);
                            if(listaExpresion.get(2) instanceof Integer){
                                List<String> varibaleValue = new ArrayList<>();
                                varibaleValue.add(String.valueOf(listaExpresion.get(2)));
                                variable.createNewVariable(variableName, varibaleValue);
                                System.out.println(variable.getVariables().containsKey(variableName));
                            }else{
                                throw new RuntimeException("Error in setq: Value incorrect");
                            }
                        }else{
                            throw new RuntimeException("Error in setq: variable name incorrect");
                        }
                    }else if(oper.equals("atom")){
                        String atom = String.valueOf(listaExpresion.get(1));
                        if(atom.length()>1){
                            return "Nil";
                        }else{
                            return "T";
                        }
                    }else if(oper.equals("cond")){
                        System.out.println("Esto entra a cond "+listaExpresion);
                        if(listaExpresion.size() == 2){
                            System.out.println("Son dos");
                        }else if(listaExpresion.size() == 3){
                            String returnIfTrue = "";
                            String resultCondition = "";
                            List<Object> condition = (List<Object>) listaExpresion.get(1);
                            System.out.println(condition.get(0));
                            System.out.println("Tamanio: "+condition.size());
                            if(condition.size() == 2){
                                List<Object> evaluateCondition = (List<Object>) condition.get(0);
                                resultCondition = String.valueOf(evaluate(evaluateCondition));
                                Object returnIfTrueCondition = condition.get(1);
                                System.out.println("Condition si es true: "+returnIfTrueCondition);
                                if(returnIfTrueCondition instanceof String){
                                    returnIfTrue = String.valueOf(symbolEvaluate(returnIfTrueCondition));
                                }else{
                                    List<Object> express = (List<Object>) returnIfTrueCondition;
                                    returnIfTrue = String.valueOf(evaluate(express));
                                }
                            }else{
                                resultCondition = String.valueOf(evaluate(condition));
                            }
                            List<Object> elseCondition = (List<Object>) listaExpresion.get(2);
                            Object conditionFalse = elseCondition.get(1);
                            String returnIfFalse = "";
                            if(conditionFalse instanceof String){
                                returnIfFalse = String.valueOf(symbolEvaluate(conditionFalse));
                            }else{
                                List<Object> toEvaluateForFalse = (List<Object>) conditionFalse;
                                returnIfFalse = String.valueOf(evaluate(toEvaluateForFalse));
                            }
                            if(resultCondition.equals("T")){
                                return returnIfTrue;
                            }else{
                                return returnIfFalse;
                            }
                            
                        }
                    }
                }else if(lispModel.getOperators().contains(oper)){
                    double res1;
                    double res2;
                    Object value1 = listaExpresion.get(1);
                    Object value2 = listaExpresion.get(2);
                    if(value1 instanceof List){
                        List<Object> v = (List<Object>) value1;
                        //res1 = (double) evaluate(v);
                        res1 = Double.parseDouble(String.valueOf(evaluate(v)));
                    }else{
                        //res1 = (double) value1;
                        if(value1 instanceof String){
                            res1 = (double) symbolEvaluate(value1);
                        }else{
                            res1 = Double.parseDouble(String.valueOf(value1));
                        }
                        
                    }
                    if(value2 instanceof List){
                        List<Object> v = (List<Object>) value2;
                        //res2 = (double) evaluate(v);
                        res2 = Double.parseDouble(String.valueOf(evaluate(v)));
                    }else{
                        //res2 = (double) value2;
                        if(value1 instanceof String){
                            res2 = (double) symbolEvaluate(value2);
                        }else{
                            res2 = Double.parseDouble(String.valueOf(value2));
                        }
                    }
                    switch(oper){
                        case"+":
                            return res1+res2;
                        case"-":
                            return res1-res2;
                        case"/":
                            if(res2 == 0.0){
                                throw new RuntimeException("Trying to divide by 0");
                            }else{
                                return res1/res2;
                            }
                        case"*":
                            return res1*res2;
                        case"^":
                            return Math.pow(res1, res2);
                        default:
                            throw new RuntimeException("incompatible character");    
                    }
    
                }else if(lispModel.getConditionals().contains(oper)){
                    String value1 = String.valueOf(symbolEvaluate(listaExpresion.get(1)));
                    String value2 = String.valueOf(symbolEvaluate(listaExpresion.get(2)));
                    switch(oper){
                        case"=":
                            return value1.equals(value2);
                        case"<":
                            if(value1.compareTo(value2) < 0){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case">":
                            if(value1.compareTo(value2) > 0){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case"equal":
                            return value1.equals(value2);
                        
                            
                   }
                }
                
            }else{
                return symbolEvaluate(first);
            } 
            if(expr.size() > 1){
                List<Object> subList = expr.subList(1, expr.size());
                return evaluate(subList);
            }
            return expr;
        }
    
        public Object symbolEvaluate(Object symbol){
            if(symbol instanceof String){
                String var = (String) symbol;
                if(variable.varibaleExist(var)){
                    return Double.parseDouble(variable.lastValue(var)); 
                }else{
                    throw new RuntimeException("Variable don't exist: "+var); 
                }
            }else if(symbol instanceof Integer){
                return (int) symbol;
            }else if(symbol instanceof Double){
                return (double) symbol;
            }
            return symbol;
        }
    
    /*Object firsValue = expr.get(0);
        System.out.println(firsValue);
        System.out.println(firsValue instanceof String);
        System.out.println(firsValue instanceof List);
        if(firsValue instanceof String){
            System.out.println("Entro: "+firsValue);
            String value = (String) firsValue;
            if(lispModel.getCommand().contains(firsValue)){
                if(value.equals("quote")){
                    return expr.get(1);
                }
                if(value.equals("eval")){
                    System.out.println("exor: "+expr);
                    Object secondValue = expr.get(1);
                    if (secondValue instanceof List) {
                        List<Object> subList = (List<Object>) secondValue;
                        System.out.println("subsub: "+subList);
                        return eval(subList);
                    } else {
                        return evaluteSymbol(secondValue);
                    }
                }else{
                    return null;
                }
            }else{
                List<Object> args = expr.subList(1, expr.size());
                Object function = eval((List<Object>) firsValue);
                if (function instanceof Function) {
                    return ((Function) function).apply(args);
                } else {
                    throw new RuntimeException("Not a function: " + function);
                }
            }
        }else if(firsValue instanceof List){
            List<Object> chain = (List<Object>) firsValue;
            List<Object> restOfExp = expr.subList(1, expr.size());
            List<Object> chainResponse = (List<Object>) eval(chain);
            restOfExp.addAll(chainResponse);
            return eval(restOfExp);
        }else{
            return evaluteSymbol(firsValue);
        }*/
    
    public Object eval(List<Object> expr){
        if (expr.isEmpty()) {
            // Empty expression evaluates to itself
            return expr;
        }
        Object first = expr.get(0);
        if(first instanceof List){
            System.out.println(first);
            List<Object> chain = (List<Object>) first;
            List<Object> restOfExp = expr.subList(1, expr.size());
            System.out.println(restOfExp);
            List<Object> chainResponse = (List<Object>) eval(chain);
            System.out.println(chainResponse);
            restOfExp.addAll(chainResponse);
            System.out.println(restOfExp);
            return eval(restOfExp);
        }else if(first instanceof String){
            System.out.println(first);
            String value = (String) first;
            if(value.equals("quote")){
                return expr.get(1);
            }
        }
        return null;
    }
    
    public Object evaluteSymbol(Object symbol){
        if(symbol instanceof Integer){
            return (int) symbol;
        }
        if(symbol instanceof String){
            String value = (String) symbol;
            if(symbol.equals(symbol)){
                return new sum();
            }
        }
        return symbol;
    }
    
    
    public String evaluate(String x){
        String toReturn = "";
        if(checkBracket(x)){
            List<String> list = separeteBrackets(x.trim().toLowerCase());
            int listPosition = 0;
            boolean isFinish = false;
            while(!isFinish){
                String command = list.get(listPosition);
                if(listPosition < list.size()){
                    if(command.charAt(0) == '('){
                        command = command.substring(1);
                    }
                    if(lispModel.getCommand().contains(command)){
                        if(command.equals("quote")){
                            list.remove(0);
                            isFinish = true;
                            if(listPosition == 0){
                                return commandControl.Quote(list);
                            }else{
                                
                            }
                        }
                    }else{
                        System.out.println(separeteBrackets(list.get(3)));
                        toReturn = "Error: No command found";
                    }
                }else{
                    toReturn = "Error: No command found";
                }
                listPosition++;
                isFinish = true;

            }
            /*if(command.charAt(0) == '('){
                command = command.substring(1);
            }
            if(lispModel.getCommand().contains(command)){
                if(command.equals("quote")){
                    list.remove(0);
                    return commandControl.Quote(list);
                }
            }else{
                toReturn = "Error: No command found";
            }*/
        }else{
            toReturn = "Error: unvalance brackets";
        }
        return toReturn;
    }
    
    public String positionLastBracket(String bracket, int numberBrackt){
        int lastBracket = numberBrackt;
        int countBracket = 0;
        int positionBracket = 0;
        int end = 0;
        String secondCut = "";
        boolean isFinish = false;
        while(!isFinish){
            char character = bracket.charAt(positionBracket);
            if(character == '('){
                countBracket++;
            }
            if(countBracket == lastBracket){
                String firstCut = bracket.substring((positionBracket+1));
                System.out.println(firstCut);
                int positionLastBracket = 0;
                int last = ((lastBracket+1)-countBracket(bracket));
                if(last == 0){
                    last = countBracket(bracket);
                }
                boolean secondIsFinish = false;
                while(!secondIsFinish){
                    character = firstCut.charAt(positionLastBracket);
                    if(character == ')'){
                        end++;
                    }
                    if(end == last){
                        secondCut = firstCut.substring(0, positionLastBracket);
                        isFinish = true;
                        secondIsFinish = true;
                    }
                    positionLastBracket++;
                }
            }
            positionBracket++;
        }
        return secondCut;
    }
    
    public int countBracket(String toCheck){
        int count = 0;
        for(int i = 0; i<toCheck.length();i++){
            char character = toCheck.charAt(i);
            if(character == '('){
                count++;
            }
        }
        return count;
    }
    
    public void lineas(String lista){
        int k;
        for(int i = 0;i<lista.length();i++){
            k = lista.indexOf("(", 0);
            
            //if(String.valueOf(lista[i].charAt(0)).equals("(")){
            //   
            //}
        }
    }
    
}
