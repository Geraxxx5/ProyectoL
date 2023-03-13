/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.FunctionModel;
import modelo.VariableModel;
import modelo.modeloLisp;

/**
 *
 * @author Gerax
 */
public class tryEvaluate {
    modeloLisp lispModel = new modeloLisp();
    public Object evaluate(List<Object> expr, VariableModel variable, FunctionModel functions){
            Object first = expr.get(0);
            //System.out.println("first: "+first);
            if (first instanceof List || ((first instanceof String)&& (expr instanceof List))) {
                List<Object> listaExpresion;
                if(first instanceof String){
                    listaExpresion = (List<Object>) expr;
                }else{
                    listaExpresion = (List<Object>) first;
                }
                //System.out.println(listaExpresion);
                String oper = (String) listaExpresion.get(0);
                //System.out.println("opeeeerrrr: "+oper);
                //System.out.println(functions.funcionExist(oper));
                if(lispModel.getCommand().contains(oper)){
                    if(oper.equals("quote") || oper.equals("\'")){
                        return listaExpresion.get(1);
                    }
                    else if(oper.equals("eval")){
                        Object second = listaExpresion.get(1);
                        if(second instanceof List){
                            List<Object> secondValue = (List<Object>) second;
                            Object response = evaluate(secondValue, variable, functions);;
                            if(response instanceof List){
                                List<Object> toEvaluate = (List<Object>) response;
                                return evaluate(toEvaluate,variable, functions);
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
                                List<Object> varibaleValue = new ArrayList<>();
                                varibaleValue.add(String.valueOf(listaExpresion.get(2)));
                                variable.createNewVariable(variableName, varibaleValue);
                                //System.out.println(variable.getVariables().containsKey(variableName));
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
                        //System.out.println("Esto entra a cond "+listaExpresion);
                        if(listaExpresion.size() == 2){
                            String returnIfTrue = "";
                            //System.out.println("Son dos");
                            List<Object> condition = (List<Object>) listaExpresion.get(1);
                            //System.out.println(condition.get(0));
                            //System.out.println("Tamanio: "+condition.size());
                            String resultCondition;
                            if(condition.size() == 2){
                                List<Object> evaluateCondition = (List<Object>) condition.get(0);
                                resultCondition = String.valueOf(evaluate(evaluateCondition,variable, functions));
                                Object returnIfTrueCondition = condition.get(1);
                                if(returnIfTrueCondition instanceof String){
                                    returnIfTrue = String.valueOf(symbolEvaluate(returnIfTrueCondition,variable));
                                }else{
                                    List<Object> express = (List<Object>) returnIfTrueCondition;
                                    returnIfTrue = String.valueOf(evaluate(express,variable, functions));
                                }
                            }else{
                                resultCondition = String.valueOf(evaluate(condition,variable, functions));
                            }
                            //System.out.println(resultCondition);
                            if(resultCondition == "T"){
                                if(returnIfTrue.equals("")){
                                    returnIfTrue = "T";
                                }
                                return returnIfTrue;
                            }else{
                                return "Nil";
                            }
                        }else if(listaExpresion.size() == 3){
                            String returnIfTrue = "";
                            String resultCondition = "";
                            List<Object> condition = (List<Object>) listaExpresion.get(1);
                            //System.out.println(condition.get(0));
                            //System.out.println("Tamanio: "+condition.size());
                            if(condition.size() == 2){
                                List<Object> evaluateCondition = (List<Object>) condition.get(0);
                                resultCondition = String.valueOf(evaluate(evaluateCondition,variable, functions));
                                Object returnIfTrueCondition = condition.get(1);
                                //System.out.println("Condition si es true: "+returnIfTrueCondition);
                                if(returnIfTrueCondition instanceof String){
                                    returnIfTrue = String.valueOf(symbolEvaluate(returnIfTrueCondition,variable));
                                }else{
                                    List<Object> express = (List<Object>) returnIfTrueCondition;
                                    returnIfTrue = String.valueOf(evaluate(express,variable, functions));
                                }
                            }else{
                                resultCondition = String.valueOf(evaluate(condition,variable, functions));
                            }
                            List<Object> elseCondition = (List<Object>) listaExpresion.get(2);
                            Object conditionFalse = elseCondition.get(1);
                            String returnIfFalse = "";
                            //System.out.println(resultCondition+" Esto esta en las condicionales?");
                            if(resultCondition.equals("T")){
                                //System.out.println("Es true? ");
                                return returnIfTrue;
                            }else{
                                if(conditionFalse instanceof String){
                                returnIfFalse = String.valueOf(symbolEvaluate(conditionFalse,variable));
                                }else{
                                    List<Object> toEvaluateForFalse = (List<Object>) conditionFalse;
                                    returnIfFalse = String.valueOf(evaluate(toEvaluateForFalse,variable, functions));
                                }
                                return returnIfFalse;
                            }
                        }
                    }else if(oper.equals("list")){
                        //System.out.println("Entro a lista");
                        List<Object> list = new ArrayList<>();
                        //System.out.println("Lsita: "+listaExpresion);
                        for(int index = 1;index<listaExpresion.size();){
                            Object toDoList = listaExpresion.get(index);
                            Object forListadd;
                            if(toDoList instanceof List){
                                List<Object> forEvaluate = (List<Object>) toDoList;
                                forListadd = evaluate(forEvaluate,variable, functions);
                            }else{
                                forListadd = toDoList;
                            }
                            list.add(forListadd);
                            index++;
                        }
                        return list;
                    }else if(oper.equals("defun")){
                        //System.out.println("Si se crea");
                        //System.out.println(listaExpresion);
                        String nameOfFunction = String.valueOf(listaExpresion.get(1));
                        if(!functions.funcionExist(nameOfFunction)){
                            List<Object> attributes = new ArrayList<>();
                            attributes.add(listaExpresion.get(2));
                            attributes.add(listaExpresion.get(3));
                            functions.createNewFunction(nameOfFunction, attributes);
                        }else{
                            throw new RuntimeException("Function alredy exist");
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
                        res1 = Double.parseDouble(String.valueOf(evaluate(v,variable, functions)));
                    }else{
                        //res1 = (double) value1;
                        if(value1 instanceof String){
                            res1 = Double.parseDouble(String.valueOf(symbolEvaluate(value1,variable)));
                            //System.out.println("res1: "+res1);
                        }else{
                            res1 = Double.parseDouble(String.valueOf(value1));
                        }
                        
                    }
                    if(value2 instanceof List){
                        List<Object> v = (List<Object>) value2;
                        //res2 = (double) evaluate(v);
                        res2 = Double.parseDouble(String.valueOf(evaluate(v,variable, functions)));
                    }else{
                        //res2 = (double) value2;
                        if(value2 instanceof String){
                            res2 = Double.parseDouble(String.valueOf(symbolEvaluate(value2,variable)));
                            //System.out.println("res2: "+res2);
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
                    double value1 = Double.parseDouble(String.valueOf(symbolEvaluate(listaExpresion.get(1),variable)));
                    double value2 = Double.parseDouble(String.valueOf(symbolEvaluate(listaExpresion.get(2),variable)));
                    switch(oper){
                        case"=":
                            //System.out.println("Son iguales en las condicionales");
                            if(value1 == value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case"<":
                            if(value1 < value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case">":
                            if(value1 > value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case"equal":
                            if(value1 == value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        default:
                            throw new RuntimeException("Eror: unexpected"); 
                   }
                //Aqui van las variables
                }else if(functions.funcionExist(oper)){
                    VariableModel varsFunction = new VariableModel();
                    List<Object> params = functions.getParams(oper);
                    List<Object> condition = functions.getCondition(oper);
                    //List<boolean>
                    //System.out.println("exprrrrr aqui: "+listaExpresion);
                    for(int index = 0;index<params.size();index++){
                        String var = (String) params.get(index);
                        //System.out.println("Entro al for");
                        if(varsFunction.varibaleExist(var)){
                            Object expressionToEvaluate = listaExpresion.get(1+index);
                            String toSave = "";
                            if(expressionToEvaluate instanceof List){
                                List<Object> toEvaluate = (List<Object>) expressionToEvaluate;
                                toSave = String.valueOf(evaluate(toEvaluate,variable, functions));
                            }else{
                                toSave = String.valueOf(expressionToEvaluate);
                            }
                            //System.out.println("Estoy intentado guardar esto: "+toSave);
                            varsFunction.getVariables().get(var).add(toSave);
                        }else{
                            List<Object> value = new ArrayList<>();
                            Object expressionToEvaluate = listaExpresion.get(1+index);
                            String toSave = "";
                            if(expressionToEvaluate instanceof List){
                                List<Object> toEvaluate = (List<Object>) expressionToEvaluate;
                                toSave = String.valueOf(evaluate(toEvaluate,variable, functions));
                            }else{
                                toSave = String.valueOf(expressionToEvaluate);
                            }
                            //System.out.println("Estoy intentado guardar esto: "+toSave);
                            value.add(toSave);
                            varsFunction.createNewVariable(var,value);
                        }
                    }
                    return evaluate(condition,varsFunction, functions);
                }
            }else{
                return symbolEvaluate(first, variable);
            } 
            if(expr.size() > 1){
                List<Object> subList = expr.subList(1, expr.size());
                return evaluate(subList,variable, functions);
            }
            return expr;
        }
    
        public Object symbolEvaluate(Object symbol,VariableModel variable){
            if(symbol instanceof String){
                String var = (String) symbol;
                if(variable.varibaleExist(var)){
                    //System.out.println("Se esta intentado parsear: "+variable.lastValue(var));
                    return Double.parseDouble(String.valueOf(variable.lastValue(var))); 
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
}
