package org.todolist;

import org.todolist.repository.DatabaseConnection;

public class Main {


    public static void main(String[] args) {

        //para probar la conexcion a la BD
        DatabaseConnection.conectar();

    }
}