package org.example;

import org.hibernate.HibernateException;

import java.io.IOException;
/**
 * Hello world!
 *
 */
public class App 
{
    private static SimpleServer server;
    public static void main( String[] args ) throws IOException {
        server = new SimpleServer(3000);
        System.out.println("server is listening");
        initializeSessionFactory();
        try{
            generateTables();
        }catch(Exception e){
            e.printStackTrace();
        }
        server.listen();
    }

    private static void initializeSessionFactory(){
        try{
            server.session = server.getSessionFactory().openSession();
        }catch (HibernateException e){
            e.printStackTrace();
        }
    }

    private static void generateTables() throws Exception{
        server.generateTasksTable();
        server.generateUsersTable();
    }
}
