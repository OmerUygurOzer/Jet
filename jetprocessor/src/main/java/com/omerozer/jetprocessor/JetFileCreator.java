package com.omerozer.jetprocessor;

import com.omerozer.jet.EventError;
import com.omerozer.jet.EventSuccess;
import com.omerozer.jet.NoError;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by omerozer on 1/27/18.
 */

class JetFileCreator {


    private Map<TypeElement, MappedClassData> classDataMap;


    JetFileCreator() {
        classDataMap = new HashMap<TypeElement, MappedClassData>();
    }

    void scanFiles(Set<? extends Element> successMethods, Set<? extends Element> errorMethods) {
        for (Element element : successMethods) {
            String[] events = element.getAnnotation(EventSuccess.class).value();
            scanFilesForSuccessMethod(element, events);
        }

        for (Element element : errorMethods) {
            String[] events = element.getAnnotation(EventError.class).value();
            scanFilesForErrorMethod(element, events);
        }
    }


    private void scanFilesForSuccessMethod(Element element, String[] events) {
        if (element.getKind().isClass() || element.getKind().isField()) {
            return;
        }
        ExecutableElement method = (ExecutableElement) element;
        TypeElement enclosingClass = (TypeElement) method.getEnclosingElement();

        if (!classDataMap.containsKey(enclosingClass)) {
            classDataMap.put(enclosingClass,
                    new MappedClassData(enclosingClass));
        }
        for (String event : events) {
            classDataMap.get(enclosingClass).getHandlerMethods(event).setSuccessMethod(method);
            classDataMap.get(enclosingClass).getHandlerMethods(event).setNoError(
                    method.getAnnotation(NoError.class) != null);
        }

    }

    private void scanFilesForErrorMethod(Element element, String[] events) {
        if (element.getKind().isClass() || element.getKind().isField()) {
            return;
        }
        ExecutableElement method = (ExecutableElement) element;
        TypeElement enclosingClass = (TypeElement) method.getEnclosingElement();

        if (!classDataMap.containsKey(enclosingClass)) {
            classDataMap.put(enclosingClass,
                    new MappedClassData(enclosingClass));
        }
        for (String event : events) {
            classDataMap.get(enclosingClass).getHandlerMethods(event).setErrorMethod(method);
        }
    }

    void createClasses(ProcessingEnvironment processingEnvironment) {
        for (MappedClassData mappedClassData : classDataMap.values()) {
            createBusClasses(mappedClassData, processingEnvironment);
        }

        JavaFileObject generatedClassFile = null;

        try {
            generatedClassFile = processingEnvironment.getFiler().createSourceFile(
                    JetFileStrings.JET_FACTORY);
            BufferedWriter bufferedWriter = new BufferedWriter(generatedClassFile.openWriter());
            JetFactoryCreator.createFactory(bufferedWriter, classDataMap);
            bufferedWriter.close();

            generatedClassFile = processingEnvironment.getFiler().createSourceFile(
                    JetFileStrings.JET_EVENT_INDEX);
            bufferedWriter = new BufferedWriter(generatedClassFile.openWriter());
            JetEventIndexCreator.createEventIndex(bufferedWriter, classDataMap);
            bufferedWriter.close();

            generatedClassFile = processingEnvironment.getFiler().createSourceFile(
                    AndroidFileStrings.ANDROID_THREAD_SWITCHER_SIMPLE);
            bufferedWriter = new BufferedWriter(generatedClassFile.openWriter());
            AndroidThreadSwitcherCreator.createSwitcher(bufferedWriter);
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createBusClasses(MappedClassData mappedClassData,
            ProcessingEnvironment processingEnvironment) {

        TypeElement clazz = mappedClassData.enclosingClass;

        try {
            JavaFileObject generatedClassFile = processingEnvironment.getFiler().createSourceFile(
                    mappedClassData.enclosingClass.getSimpleName() + JetFileStrings.JET_POSTFIX);
            BufferedWriter bufferedWriter = new BufferedWriter(generatedClassFile.openWriter());
            bufferedWriter.newLine();
            JetBusCreator.createBusClass(bufferedWriter, clazz, mappedClassData.handlerMethodsMap);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
