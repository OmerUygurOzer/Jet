package com.omerozer.jetprocessor;

import com.omerozer.jet.EventError;
import com.omerozer.jet.EventSuccess;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import java.util.Set;

/**
 * Created by omerozer on 1/26/18.
 */

public class JetProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"Jet Processor Running");

        JetFileCreator jetFileCreator = new JetFileCreator();

        jetFileCreator.scanFiles(roundEnvironment.getElementsAnnotatedWith(EventSuccess.class), roundEnvironment.getElementsAnnotatedWith(EventError.class));

        jetFileCreator.createClasses(processingEnv);

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return JetAnnotations.getAll();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
