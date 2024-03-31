package org.example;

import com.google.protobuf.Empty;
import org.example.ocsf.AbstractServer;
import org.example.ocsf.ConnectionToClient;
import org.example.ocsf.SubscribedClient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Index;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

public class SimpleServer extends AbstractServer {
    private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
    private static Session session;
    private static SessionFactory sessionFactory = getSessionFactory();

    public SimpleServer(int port) {
        super(port);
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            generateUsersTable();
            generateTasksTable();
            session.getTransaction().commit();
        } catch (Exception var5) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            var5.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void printAllTasks() {
        List<Task> tasks = getAllTasks();
        for (Task task : tasks) {
            System.out.println(task.getTaskName());
        }
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Task.class);
        configuration.addAnnotatedClass(Users.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static List<Task> getAllTasks() {
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Task> query = builder.createQuery(Task.class);
            Root<Task> root = query.from(Task.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    private static void modifyTask(int TaskID) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Task> query = builder.createQuery(Task.class);
            Root<Task> root = query.from(Task.class);
            query.where(builder.equal(root.get("TaskID"), TaskID));
            List<Task> tasks = session.createQuery(query).getResultList();
            for (Task task : tasks) {
                task.setState("in progress");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void generateTasksTable() {
        LocalDateTime now = LocalDateTime.now();
        Task task1 = new Task(1829371289, "Task1", "Walk the pets", "Eden Daddo", 2128219878, "Pending", now, "none");
        Task task2 = new Task(1829371284, "Task2", "Buy medical equipment", "Leen Yakov", 1823718982, "Pending", now, "none");
        Task task3 = new Task(1829371288, "Task3", "Help buy groceries", "Leen Yakov", 1823718982, "Pending", now, "none");
        Task task4 = new Task(1829371287, "Task4", "Clean the house", "Karen Yakov", 2127726318, "Pending", now, "none");
        Task task5 = new Task(1829371286, "Task5", "Take care of the children", "Karen Yakov", 2127726318, "Pending", now, "none");
        Task task6 = new Task(1829371285, "Task6", "Give a ride", "Eden daddo", 2128219878, "Pending", now, "none");
        session.save(task1);
        session.save(task2);
        session.save(task3);
        session.save(task4);
        session.save(task5);
        session.save(task6);
        session.flush();
    }

    public static void generateUsersTable() {
        Users user1 = new Users("Eden Daddo", "Eden11", 2128219878, "edenDado@gmail.com", "Haifa Remot Remez Haviva Reich 54", 547823641, 1, "User");
        Users user2 = new Users("Karen Yakov", "Karen11", 2127726318, "karenYakov@gmail.com", "Haifa Remot Alon Dovnov 18", 547374388, 2, "User");
        Users user3 = new Users("Leen Yakov", "Leen12", 1823718982, "leenYakov@gmail.com", "Haifa Remot Alon Dovnov 16", 518723618, 2, "User");
        Users user4 = new Users("Rami Benet", "Rami11", 2138291782, "ramiBenet@gmail.com", "Haifa Remot Remez Haviva Reich 60", 534289782, 1, "User");
        Users user5 = new Users("Abo Majd", "Majd5", 1212323982, "aboMajd@gmail.com", "Haifa Remot Remez Haviva Reich 40", 541271872, 3, "User");
        Users user6 = new Users("Yonatan Boris", "Yonatan12", 1728631726, "yonatanBoris@gmail.com", "Haifa Neve Shanan Netiv Hen 12", 541782631, 3, "User");
        Users user7 = new Users("Louis Litt", "Louis15", 1237298379, "louisLitt@gmail.com", "Haifa Neve Shanan Netiv Hen 10", 576718722, 2, "User");
        Users user8 = new Users("Yohanan Bloomfield", passwordEncrypt("Yohanan16"), 1213798179, "yohananBloomfield@gmail.com", "Haifa Neve Shanan Hanita 24", 542882281, 1, "Manager");
        session.save(user1);
        session.save(user2);
        session.save(user3);
        session.save(user4);
        session.save(user5);
        session.save(user6);
        session.save(user7);
        session.save(user8);
        session.flush();
    }

    public static void handleNewUser(String fullName, int userID, String emailAddress, String password,
                                     String address, int phoneNumber, int groupID){
            Users newUser = new Users(fullName, passwordEncrypt(password), userID, emailAddress, address, phoneNumber,groupID, "User");
            session.save(newUser);
            session.flush();
    }

    public static String passwordEncrypt(String password){
        String salt= "csgo";
        try {
            String passwordWithSalt = password + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(passwordWithSalt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        try {
            String message = (String) msg;
            if (message.equals("get tasks")) {
                client.sendToClient(getAllTasks());
            } else if (message.startsWith("modify")) {
                String taskid = message.split(" ")[1];
                modifyTask(Integer.parseInt(taskid));
                client.sendToClient(getAllTasks());
            } else if (message.equals("add client")) {
                SubscribedClient connection = new SubscribedClient(client);
                SubscribersList.add(connection);
                message = "client added successfully";
                client.sendToClient(message);
            } else if (message.equals("Emergency")) {
                System.out.println("we have an emergency");
            } else if (message.startsWith("Register")) {
                String[] userData = message.split(",");
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Users> query = builder.createQuery(Users.class);
                    Root<Users> root = query.from(Users.class);
                    query.where(builder.equal(root.get("EmailAddress"), userData[3]));
                    List<Users> users = session.createQuery(query).getResultList();
                    if(!users.isEmpty()){
                        client.sendToClient("exists");
                    }else{
                        handleNewUser(userData[1],Integer.parseInt(userData[2]),userData[3],userData[4]
                                ,userData[5],Integer.parseInt(userData[6]),Integer.parseInt(userData[7]));
                        client.sendToClient("doesn't exist");
                        System.out.println("Registered successfuly");
                    }
                    session.getTransaction().commit();
                } catch (Exception var5) {
                    if (session != null && session.getTransaction().isActive()) {
                        session.getTransaction().rollback();
                    }
                    var5.printStackTrace();
                } finally {
                    if (session != null) {
                        session.close();
                    }
                }
            } else if (message.startsWith("LogIn")) {
                String[] userData = message.split(",");
                session = sessionFactory.openSession();
                session.beginTransaction();
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<Users> query = builder.createQuery(Users.class);
                Root<Users> root = query.from(Users.class);
                query.where(builder.equal(root.get("EmailAddress"), userData[1]));
                List<Users> users = session.createQuery(query).getResultList();
                if(!users.isEmpty()){
                    for(Users user1 : users){
                        System.out.println(user1.getPassword());
                        System.out.println(passwordEncrypt(userData[2]));
                        if(user1.getPassword().equals(passwordEncrypt(userData[2]))){
                            client.sendToClient("LogIn," + user1.getFullName() + "," + user1.getUserID()
                            + "," + user1.getEmailAddress() + "," + user1.getPassword() + "," + user1.getHomeAddress()
                            + "," + user1.getPhoneNumber() + "," + user1.getGroupID() + "," + user1.getTitle());
                        }else{
                            client.sendToClient("WrongPassword");
                        }
                    }
                }else{
                    client.sendToClient("Don't LogIn");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    public void sendToAllClients(Task message) {
        try {
            for (SubscribedClient SubscribedClient : SubscribersList) {
                SubscribedClient.getClient().sendToClient(message);
            }
        } catch (IOException e1) {
            e1.printStackTrace();

        }
    }

}
