package com.omerozer.jetprocessor;

import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by omerozer on 1/28/18.
 */
public class JetEventIndexCreator {
    static void  createEventIndex(BufferedWriter bufferedWriter, Map<TypeElement,MappedClassData> classes) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_WARNING_COMMENT);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_CORE_PACKAGE);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" class ");
        bufferedWriter.append(JetFileStrings.JET_EVENT_INDEX);
        bufferedWriter.append(" implements ");
        bufferedWriter.append(JetFileStrings.EVENT_INDEX_INTERFACE);
        bufferedWriter.append("{");
        bufferedWriter.newLine();


        for(TypeElement enclosing: classes.keySet()){
            bufferedWriter.newLine();
            bufferedWriter.append(JetFileStrings.STRING_CLASS);
            bufferedWriter.append("[] ");
            bufferedWriter.append(JetFileStrings.JET_EVENT_PREFIX);
            bufferedWriter.append(enclosing.getSimpleName());
            bufferedWriter.append(" = ");
            bufferedWriter.append(JetFileStrings.createStringArrayField(classes.get(enclosing).handlerMethodsMap.keySet()));
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.JET_EVENT_INDEX);
        bufferedWriter.append("(){}");
        bufferedWriter.newLine();//Empty public constructor;

        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.STRING_CLASS);
        bufferedWriter.append("[] ");
        bufferedWriter.append("getEventsForParent(");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" parent){");
        bufferedWriter.newLine();
        for(TypeElement enclosing: classes.keySet()){
            bufferedWriter.append("if(parent instanceof ");
            bufferedWriter.append(enclosing.getQualifiedName());
            bufferedWriter.append("){");
            bufferedWriter.newLine();
            bufferedWriter.append("return ");
            bufferedWriter.append(JetFileStrings.JET_EVENT_PREFIX);
            bufferedWriter.append(enclosing.getSimpleName());
            bufferedWriter.append(";");
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
