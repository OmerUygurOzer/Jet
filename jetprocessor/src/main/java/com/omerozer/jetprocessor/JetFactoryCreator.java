package com.omerozer.jetprocessor;

import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by omerozer on 1/28/18.
 */

public class JetFactoryCreator  {

    static void  createFactory(BufferedWriter bufferedWriter, Map<TypeElement,MappedClassData> classes) throws IOException{
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_WARNING_COMMENT);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_CORE_PACKAGE);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" class ");
        bufferedWriter.append(JetFileStrings.JET_FACTORY);
        bufferedWriter.append(" implements ");
        bufferedWriter.append(JetFileStrings.FACTORY_INTERFACE);
        bufferedWriter.append("{");
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.EVENT_HANDLER_ABSTRACT_CLASS);
        bufferedWriter.append(" create(");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" parent){");
        bufferedWriter.newLine();
        for(TypeElement enclosing: classes.keySet()){
            bufferedWriter.append("if(parent instanceof ");
            bufferedWriter.append(enclosing.getQualifiedName());
            bufferedWriter.append("){");
            bufferedWriter.newLine();
            bufferedWriter.append("return ");
            bufferedWriter.append("new ");
            bufferedWriter.append(enclosing.getQualifiedName());
            bufferedWriter.append(JetFileStrings.JET_POSTFIX);
            bufferedWriter.append("(");
            bufferedWriter.append("parent);");
            bufferedWriter.newLine();
            bufferedWriter.append("}");
        }

        bufferedWriter.newLine();
        bufferedWriter.append("return null;");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

}
