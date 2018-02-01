package com.omerozer.jetprocessor;

import java.util.Collection;

/**
 * Created by omerozer on 1/28/18.
 */

class JetFileStrings {

   static final String JET_WARNING_COMMENT = "//Created by Jet. Do NOT alter contents of this class.";
   static final String JET_CORE_PACKAGE = "package com.omerozer.jetcore;";
   static final String JET_POSTFIX = "_Jet";
   static final String JET_FACTORY = "JetFactory";
   static final String JET_EVENT_INDEX = "JetEventIndex";
   static final String JET_EVENT_PREFIX = "jetEvent_";
   static final String PUBLIC = "public";
   static final String PRIVATE = "private";
   static final String STATIC = "static";
   static final String WEAK_REF = "java.lang.ref.WeakReference";
   static final String PARENT_WEAK_REF = "parentWeakRef";
   static final String GET_PARENT = "getParent";
   static final String EVENT_SUCCESS = "handleSuccess";
   static final String EVENT_FAILURE = "handleError";
   static final String OBJECT_CLASS = Object.class.getCanonicalName();
   static final String STRING_CLASS = String.class.getCanonicalName();
   static final String THREAD_SWITCHER_INTERFACE = "com.omerozer.jetcore.ThreadSwitcherInterface";
   static final String FACTORY_INTERFACE = "com.omerozer.jetcore.JetFactoryInterface";
   static final String EVENT_INDEX_INTERFACE = "com.omerozer.jetcore.JetEventIndexInterface";
   static final String EVENT_HANDLER_ABSTRACT_CLASS = "com.omerozer.jet.EventHandler";

   static String createStringArrayField(Collection<String> strings){
       String[] stringsArray = new String[strings.size()];

       StringBuilder builder = new StringBuilder();
       builder.append("new ");
       builder.append(STRING_CLASS);
       builder.append("[]{");

       int c = 0;

       for(String event : strings){
           stringsArray[c++] = event;
       }

       for(int i = 0 ;i < strings.size(); i++){
           builder.append("\"");
           builder.append(stringsArray[i]);
           builder.append("\"");
           if(i!=stringsArray.length-1){
               builder.append(",");
           }
       }
       builder.append("};");
       return builder.toString();
   }

}
