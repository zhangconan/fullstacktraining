package com.zkn.fullstacktraining.ninth.dynamicscript;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;

/**
 * Created by wb-zhangkenan on 2017/3/7.
 * https://www.ibm.com/developerworks/cn/java/j-javascripting2/index.html
 * https://www.ibm.com/developerworks/cn/java/j-lo-jse66/
 *
 * @author wb-zhangkenan
 * @date 2017/03/07
 */
public class GroovyScriptEngineExample {

    public static void main(String[] args) {

        String[] roots = new String[]{"D:\\CUST\\WORK\\Exercises\\FullStackTraining\\src\\main\\java\\com\\zkn\\fullstacktraining\\ninth\\dynamicscript\\"};
        try {
            GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine(roots);
            Binding binding = new Binding();
//            binding.setVariable("language","New Groovy");
//            Object obj = groovyScriptEngine.run("SimpleScript.msp",binding);
//            System.out.println(obj);
            binding.setVariable("name","name123");
            binding.setVariable("password","password01");
            Object obj = groovyScriptEngine.run("LogicScript.msp",binding);
            System.out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
