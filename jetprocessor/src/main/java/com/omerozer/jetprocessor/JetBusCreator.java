package com.omerozer.jetprocessor;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by omerozer on 1/28/18.
 */

class JetBusCreator {

    static void createBusClass(BufferedWriter bufferedWriter,TypeElement clazz,Map<String,HandlerMethods> handlerMethodsMap)throws IOException{
        initClass(bufferedWriter,clazz);
        createWeakParentField(bufferedWriter,clazz);
        createConstructorForParent(bufferedWriter, clazz);
        createParentGetter(bufferedWriter, clazz);
        overrideEventSuccess(bufferedWriter, handlerMethodsMap);
        overrideEventFailure(bufferedWriter, handlerMethodsMap);
        createEventsGetter(bufferedWriter,handlerMethodsMap);
        createParentAccessor(bufferedWriter);
        endClass(bufferedWriter);
    }

    private static void initClass(BufferedWriter bufferedWriter,TypeElement typeElement) throws IOException {
        PackageElement packageElement = (PackageElement)typeElement.getEnclosingElement();
        bufferedWriter.newLine();
        bufferedWriter.append("package ");
        bufferedWriter.append(packageElement.getQualifiedName());
        bufferedWriter.append(";");
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_WARNING_COMMENT);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" class ");
        bufferedWriter.append(typeElement.getSimpleName());
        bufferedWriter.append(JetFileStrings.JET_POSTFIX);
        bufferedWriter.append(" extends ");
        bufferedWriter.append(JetFileStrings.EVENT_HANDLER_ABSTRACT_CLASS);
        bufferedWriter.append("{");
        bufferedWriter.newLine();
    }

    private static void createWeakParentField(BufferedWriter bufferedWriter,TypeElement typeElement) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PRIVATE);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.WEAK_REF);
        bufferedWriter.append("<");
        bufferedWriter.append(typeElement.getQualifiedName());
        bufferedWriter.append("> ");
        bufferedWriter.append(JetFileStrings.PARENT_WEAK_REF);
        bufferedWriter.append(";");
        bufferedWriter.newLine();
    }

    private static void createConstructorForParent(BufferedWriter bufferedWriter, TypeElement typeElement) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(typeElement.getSimpleName());
        bufferedWriter.append(JetFileStrings.JET_POSTFIX);
        bufferedWriter.append("(");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" parent){");
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PARENT_WEAK_REF);
        bufferedWriter.append("= new ");
        bufferedWriter.append(JetFileStrings.WEAK_REF);
        bufferedWriter.append("<");
        bufferedWriter.append(typeElement.getQualifiedName());
        bufferedWriter.append(">((");
        bufferedWriter.append(typeElement.getQualifiedName());
        bufferedWriter.append(")parent);");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void createParentGetter(BufferedWriter bufferedWriter, TypeElement typeElement) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PRIVATE);
        bufferedWriter.append(" ");
        bufferedWriter.append(typeElement.getQualifiedName());
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.GET_PARENT);
        bufferedWriter.append("(){");
        bufferedWriter.newLine();
        bufferedWriter.append("return ");
        bufferedWriter.append("(");
        bufferedWriter.append(typeElement.getQualifiedName());
        bufferedWriter.append(")");
        bufferedWriter.append(JetFileStrings.PARENT_WEAK_REF);
        bufferedWriter.append(".get();");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void overrideEventSuccess(BufferedWriter bufferedWriter,Map<String,HandlerMethods> handlerMethodsMap) throws IOException{
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" void ");
        bufferedWriter.append(JetFileStrings.EVENT_SUCCESS);
        bufferedWriter.append("(");
        bufferedWriter.append(JetFileStrings.STRING_CLASS);
        bufferedWriter.append(" event,");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" message){");
        bufferedWriter.newLine();

        for (String event : handlerMethodsMap.keySet()){
            bufferedWriter.newLine();
            bufferedWriter.append("if(event.equals(\"");
            bufferedWriter.append(event);
            bufferedWriter.append("\")){");
            bufferedWriter.newLine();
            bufferedWriter.append("getParent().");
            bufferedWriter.append(handlerMethodsMap.get(event).successMethod.getSimpleName());
            bufferedWriter.append("(message);");
            bufferedWriter.newLine();
            bufferedWriter.append("return;");
            bufferedWriter.append("}");
            bufferedWriter.newLine();
        }



        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void overrideEventFailure(BufferedWriter bufferedWriter,Map<String,HandlerMethods> handlerMethodsMap) throws IOException{
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" void ");
        bufferedWriter.append(JetFileStrings.EVENT_FAILURE);
        bufferedWriter.append("(");
        bufferedWriter.append(JetFileStrings.STRING_CLASS);
        bufferedWriter.append(" event,");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" message){");
        bufferedWriter.newLine();

        for (String event : handlerMethodsMap.keySet()){
            if(handlerMethodsMap.get(event).noError){
                continue;
            }
            bufferedWriter.newLine();
            bufferedWriter.append("if(event.equals(\"");
            bufferedWriter.append(event);
            bufferedWriter.append("\")){");
            bufferedWriter.newLine();
            bufferedWriter.append("getParent().");
            bufferedWriter.append(handlerMethodsMap.get(event).errorMethod.getSimpleName());
            bufferedWriter.append("(message);");
            bufferedWriter.newLine();
            bufferedWriter.append("return;");
            bufferedWriter.append("}");
            bufferedWriter.newLine();
        }



        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void createEventsGetter(BufferedWriter bufferedWriter,Map<String,HandlerMethods> handlerMethodsMap) throws IOException{
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.STRING_CLASS);
        bufferedWriter.append("[] getEvents(){");
        bufferedWriter.newLine();
        bufferedWriter.append("return ");
        bufferedWriter.append(JetFileStrings.createStringArrayField(handlerMethodsMap.keySet()));
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void createParentAccessor(BufferedWriter bufferedWriter) throws IOException{
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(JetFileStrings.OBJECT_CLASS);
        bufferedWriter.append(" ");
        bufferedWriter.append("accessParent(){");
        bufferedWriter.newLine();
        bufferedWriter.append("return getParent();");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

    private static void endClass(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }

}
