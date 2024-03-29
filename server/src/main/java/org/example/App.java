package org.example;

import org.hibernate.HibernateException;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    private static SimpleServer server;

    public static void main(String[] args) throws Exception {
        server = new SimpleServer(3000);
        System.out.println("server is listening");
        server.listen();
    }
}
