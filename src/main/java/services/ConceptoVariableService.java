/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import db.ConceptoVariable;
import fachade.ConceptoVariableRepository;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.inject.Inject;
import javax.inject.Named;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author jk
 */
@Named
public class ConceptoVariableService implements Serializable {

    @Inject
    private ConceptoVariableRepository cvr;

    private char[] operators = new char[]{'*', '/', '+', '-', '(', ')'};
    private String delim = new String(operators);

    private boolean tieneConceptos(String formula) {
        int contador = 0;

        StringTokenizer st = new StringTokenizer(formula, delim);
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();

            if ((!token.isEmpty()) && (!NumberUtils.isNumber(token)) && (!token.equals("ERROR"))) {
                contador++;
                System.err.println(contador);
            }
        }

        System.err.println(contador > 0);
        return (contador > 0);
    }

    public Double evaluar(String formula) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        while (tieneConceptos(formula)) {
            StringTokenizer st = new StringTokenizer(formula, delim);
            System.err.println(formula);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                token = token.trim();

                if ((!token.isEmpty()) && (!NumberUtils.isNumber(token))) {
                    System.err.println(token);

                    
                    ConceptoVariable cv = cvr.findOptionalByNombre(token);
                    System.err.println(cv);
                    if (cv != null) {
                        formula = formula.replace(token, "(" + cv.getFormula() + ")");
                    } else {
                        System.out.println("ERROR");
                        formula = formula.replace(token, "(ERROR)");
                    }
                    

                }
            }
        }

        System.err.println("Formula: " + formula);

         if (formula.contains("ERROR")) {
            return -1D;
        } else {
            Object result = engine.eval(formula);
            try {
                return (Double) result;
            } catch (ClassCastException e) {
                return new Double((Integer) result);
            }
        }
        
 

    }

}
