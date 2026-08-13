package org.todolist;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatLauncher {

    public static void main(String[] args) throws Exception {

        int port = 8080;

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        // Usar la aplicación web ya ensamblada por Maven
        String webappDir = new File(
                "target/ToDoListAvanzado-1.0-SNAPSHOT"
        ).getAbsolutePath();

        Context context = tomcat.addWebapp(
                "",
                webappDir
        );

        context.setParentClassLoader(
                Thread.currentThread().getContextClassLoader()
        );

        System.out.println();
        System.out.println("======================================");
        System.out.println(" ToDoListAvanzado");
        System.out.println(" Tomcat iniciado correctamente");
        System.out.println(" http://localhost:8080");
        System.out.println("======================================");
        System.out.println();

        tomcat.start();

        tomcat.getServer().await();
    }
}

