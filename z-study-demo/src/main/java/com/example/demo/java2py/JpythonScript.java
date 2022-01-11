package com.example.demo.java2py;

import org.python.util.PythonInterpreter;

import java.util.Properties;

/**
 * Java调用python脚本2
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/11
 */
public class JpythonScript {
    public static void main(String args[]) {
        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
        interpreter.exec("print days[1];");

        interpreter.exec("sum = int(2) + int(4)");
        interpreter.exec("print(sum)");
    }
}