package com.omerozer.jetprocessor;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by omerozer on 1/29/18.
 */
public class AndroidThreadSwitcherCreator {
    static void createSwitcher(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_WARNING_COMMENT);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.JET_CORE_PACKAGE);
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" class ");
        bufferedWriter.append(AndroidFileStrings.ANDROID_THREAD_SWITCHER_SIMPLE);
        bufferedWriter.append(" implements ");
        bufferedWriter.append(JetFileStrings.THREAD_SWITCHER_INTERFACE);
        bufferedWriter.append("{");
        bufferedWriter.newLine();
        bufferedWriter.append(JetFileStrings.PUBLIC);
        bufferedWriter.append(" ");
        bufferedWriter.append(AndroidFileStrings.ANDROID_THREAD_SWITCHER_SIMPLE);
        bufferedWriter.append(" (){}");
        bufferedWriter.newLine();
        bufferedWriter.append("private long threadId = Thread.currentThread().getId();");
        bufferedWriter.newLine();
        bufferedWriter.append("private ");
        bufferedWriter.append(AndroidFileStrings.HANDLER_CLASS);
        bufferedWriter.append(" handler = new ");
        bufferedWriter.append(AndroidFileStrings.HANDLER_CLASS);
        bufferedWriter.append("(");
        bufferedWriter.append(AndroidFileStrings.LOOPER);
        bufferedWriter.append(".");
        bufferedWriter.append(AndroidFileStrings.GET_MAIN_LOOPER);
        bufferedWriter.append(");");
        bufferedWriter.newLine();
        bufferedWriter.append("public void handleTask(Runnable runnable){");
        bufferedWriter.newLine();
        bufferedWriter.append("if(threadId == Thread.currentThread().getId()){");
        bufferedWriter.newLine();
        bufferedWriter.append("runnable.run();");
        bufferedWriter.newLine();
        bufferedWriter.append("return;}");
        bufferedWriter.newLine();
        bufferedWriter.append("handler.post(runnable);}");
        bufferedWriter.newLine();
        bufferedWriter.append("}");
    }
}
