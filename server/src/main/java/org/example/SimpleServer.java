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
import java.time.LocalDate;
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
            generateReportsTable();
            generateMessagesTable();
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

    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Task.class);
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Reports.class);
        configuration.addAnnotatedClass(NotificationMessage.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static <T> List<T> getAll(Class<T> object) {
        Session session1 = null;
        try {
            session1 = sessionFactory.openSession();
            System.out.println(session);
            CriteriaBuilder builder = session1.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(object);
            Root<T> root = query.from(object);

            query.select(root);
            return session1.createQuery(query).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            if (session1 != null && session1.getTransaction().isActive()) {
                session1.getTransaction().rollback();
            }
        } finally {
            if (session1 != null) {
                session1.close();
            }
        }
        return null;
    }

    private static void modifyTask(int TaskID, String msg, String name) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Task> query = builder.createQuery(Task.class);
            Root<Task> root = query.from(Task.class);
            query.where(builder.equal(root.get("TaskID"), TaskID));
            List<Task> tasks = session.createQuery(query).getResultList();
            for (Task task : tasks) {
                if (msg.equals("TasksList")) {
                    task.setState("in progress");
                    task.setVolunteer(name);
                } else if (msg.equals("Authorized")) {
                    task.setAuthorized("Authorized");
                } else if (msg.equals("Unauthorized") || msg.equals("Task Completed")) {
                    session.delete(task);
                }
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

    public static void generateReportsTable() {
        LocalDateTime now = LocalDateTime.now();
        Reports report1 = new Reports("report1", 1231232, 2128219878, "Eden Daddo", now, "blackburn");
        Reports report2 = new Reports("report2", 1231232, 2128219878, "Eden Daddo", now, "blackburn");
        Reports report3 = new Reports("report3", 1231232, 2128219878, "Leen Yakov", now, "london");
        Reports report4 = new Reports("report4", 1231232, 2128219878, "Eden Daddo", now, "blackburn");
        Reports report5 = new Reports("report5", 1231232, 2128219878, "Rami Benet", now, "liverpool");
        Reports report6 = new Reports("report6", 1231232, 2128219878, "Rami Benet", now, "liverpool");
        Reports report7 = new Reports("report7", 1231232, 2128219878, "Rami Benet", now, "liverpool");
        Reports report8 = new Reports("report8", 1231232, 2128219878, "Karen Yakov", now.minusHours(3), "london");
        session.save(report1);
        session.save(report2);
        session.save(report3);
        session.save(report4);
        session.save(report5);
        session.save(report6);
        session.save(report7);
        session.save(report8);
        session.flush();
    }

    public static void generateMessagesTable() {
        LocalDateTime now = LocalDateTime.now();
        NotificationMessage message1 = new NotificationMessage(1, "Incompatible", now, "Yohanan Bloomfield", "Louis Litt");
        NotificationMessage message2 = new NotificationMessage(1, "incomplete", now, "Yohanan Bloomfield", "Karen Yakov");
        NotificationMessage message3 = new NotificationMessage(1, "Lack of info", now, "Yohanan Bloomfield", "Louis Litt");
        NotificationMessage message4 = new NotificationMessage(1, "simple task", now, "Yohanan Bloomfield", "Karen Yakov");
        NotificationMessage message5 = new NotificationMessage(1, "try again", now, "Yohanan Bloomfield", "Louis Litt");
        session.save(message1);
        session.save(message2);
        session.save(message3);
        session.save(message4);
        session.save(message5);
        session.flush();
    }

    public static void generateTasksTable() {
        LocalDateTime now = LocalDateTime.now();
        String dueDate = now.toString();
        Task task1 = new Task(1829371289, "Task1", "Walk the pets", "Eden Daddo", 2128219878, "Pending", now, "none", "Authorized", dueDate);
        Task task2 = new Task(1829371284, "Task2", "Buy medical equipment", "Leen Yakov", 1823718982, "Pending", now, "none", "Authorized", dueDate);
        Task task3 = new Task(1829371288, "Task3", "Help buy groceries", "Leen Yakov", 1823718982, "Pending", now, "none", "Authorized", dueDate);
        Task task4 = new Task(1829371287, "Task4", "Clean the house", "Karen Yakov", 2127726318, "in progress", now, "Eden Daddo", "Authorized", dueDate);
        Task task5 = new Task(1829371286, "Task5", "Take care of the children", "Karen Yakov", 2127726318, "in progress", now, "Eden Daddo", "Authorized", dueDate);
        Task task6 = new Task(1829371285, "Task6", "Give a ride", "Rami Benet", 2138291782, "Pending", now, "none", "Authorized", dueDate);
        Task task7 = new Task(1829371285, "Task7", "Give a ride", "Rami Benet", 2138291782, "Pending", now, "none", "Authorized", dueDate);
        Task task8 = new Task(1829371285, "Task8", "Give a ride", "Eden Daddo", 2128219878, "Pending", now, "none", "Unauthorized", dueDate);
        Task task9 = new Task(1829371285, "Task9", "Give a ride", "Eden Daddo", 2128219878, "Pending", now, "none", "Unauthorized", dueDate);
        Task task10 = new Task(1829371285, "Task10", "Give a ride", "Eden Daddo", 2128219878, "Pending", now, "none", "Unauthorized", dueDate);

        session.save(task1);
        session.save(task2);
        session.save(task3);
        session.save(task4);
        session.save(task5);
        session.save(task6);
        session.save(task7);
        session.save(task8);
        session.save(task9);
        session.save(task10);
        session.flush();
    }

    public static void generateUsersTable() {
        Users user1 = new Users("Eden Daddo", passwordEncrypt("Eden11"), 2128219878, "edenDado@gmail.com", "Haifa Remot Remez Haviva Reich 54", 547823641, 1, "User", false);
        Users user2 = new Users("Karen Yakov", passwordEncrypt("Karen11"), 2127726318, "karenYakov@gmail.com", "Haifa Remot Alon Dovnov 18", 547374388, 2, "User", false);
        Users user3 = new Users("Leen Yakov", passwordEncrypt("Leen12"), 1823718982, "leenYakov@gmail.com", "Haifa Remot Alon Dovnov 16", 518723618, 2, "User", false);
        Users user4 = new Users("Rami Benet", passwordEncrypt("Rami11"), 2138291782, "ramiBenet@gmail.com", "Haifa Remot Remez Haviva Reich 60", 534289782, 1, "User", false);
        Users user5 = new Users("Abo Majd", passwordEncrypt("Majd5"), 1212323982, "aboMajd@gmail.com", "Haifa Remot Remez Haviva Reich 40", 541271872, 3, "User", false);
        Users user6 = new Users("Yonatan Boris", passwordEncrypt("Yonatan12"), 1728631726, "yonatanBoris@gmail.com", "Haifa Neve Shanan Netiv Hen 12", 541782631, 3, "User", false);
        Users user7 = new Users("Louis Litt", passwordEncrypt("Louis15"), 1237298379, "louisLitt@gmail.com", "Haifa Neve Shanan Netiv Hen 10", 576718722, 2, "User", false);
        Users user8 = new Users("Yohanan Bloomfield", passwordEncrypt("Yohanan16"), 1213798179, "yohananBloomfield@gmail.com", "Haifa Neve Shanan Hanita 24", 542882281, 1, "Manager", false);
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
                                     String address, int phoneNumber, int groupID) {
        Users newUser = new Users(fullName, passwordEncrypt(password), userID, emailAddress, address, phoneNumber, groupID, "User", false);
        session.save(newUser);
        session.flush();
    }

    public static String passwordEncrypt(String password) {
        String salt = "encrypt";
        try {
            String passwordWithSalt = password + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(passwordWithSalt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        try {
            String message = (String) msg;
            if (message.equals("get tasks")) {
                client.sendToClient(getAll(Task.class));
            } else if (message.startsWith("modify")) {
                String taskid = message.split(",")[1];
                String volunteer = message.split(",")[2];
                modifyTask(Integer.parseInt(taskid), "TasksList", volunteer);
                client.sendToClient(getAll(Task.class));
            } else if (message.equals("add client")) {
                SubscribedClient connection = new SubscribedClient(client);
                SubscribersList.add(connection);
                message = "client added successfully";
                client.sendToClient(message);
            } else if (message.startsWith("Emergency")) {
                String[] temp = message.split(",");
                LocalDateTime now = LocalDateTime.now();
                Reports report = new Reports(temp[1], Integer.parseInt(temp[2]),
                        Integer.parseInt(temp[3]), temp[4], now, temp[5]);
                NotificationMessage newMessage = new NotificationMessage(1, report.getReportName(),
                        now, report.getFullName(), "HelpCenter");
                add(report);
                add(newMessage);
                System.out.println("We have an emergency. Details: ");
                System.out.println(report);
                client.sendToClient("Emergency call was received\nHelp is on the way\nEmergency Details:\n" + report);
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
                    if (!users.isEmpty()) {
                        client.sendToClient("exists");
                    } else {
                        handleNewUser(userData[1], Integer.parseInt(userData[2]), userData[3], userData[4]
                                , userData[5], Integer.parseInt(userData[6]), Integer.parseInt(userData[7]));
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
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Users> query = builder.createQuery(Users.class);
                    Root<Users> root = query.from(Users.class);
                    query.where(builder.equal(root.get("EmailAddress"), userData[1]));
                    List<Users> users = session.createQuery(query).getResultList();
                    if (!users.isEmpty()) {
                        for (Users user1 : users) {
                            System.out.println(session.isOpen());
                            if (user1.getPassword().equals(passwordEncrypt(userData[2])) && !user1.isActive()) {
                                user1.setActive(true);
                                session.save(user1);
                                session.flush();
                                client.sendToClient("LogIn," + user1.getFullName() + "," + user1.getUserID()
                                        + "," + user1.getEmailAddress() + "," + user1.getPassword() + "," + user1.getHomeAddress()
                                        + "," + user1.getPhoneNumber() + "," + user1.getGroupID() + "," + user1.getTitle());
                                System.out.println(session.isOpen());
                                client.sendToClient(getAll(NotificationMessage.class));
                                System.out.println(session.isOpen());
                            } else {
                                client.sendToClient("WrongPassword");
                            }
                        }
                    } else {
                        client.sendToClient("Don't LogIn");
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
            } else if (message.startsWith("HelpRequest")) {
                String[] HelpRequest = message.split(",");
                LocalDateTime now = LocalDateTime.now();
                Task tempTask = new Task(1, HelpRequest[6], HelpRequest[1] + " " + HelpRequest[2], HelpRequest[4]
                        , Integer.parseInt(HelpRequest[5]), "Pending", now, "none", "Unauthorized", HelpRequest[3]);
                add(tempTask);
            } else if (message.startsWith("Task Accepted,")) {
                String accept = message.split(",")[1];
                modifyTask(Integer.parseInt(accept), "Authorized", "");
            } else if (message.startsWith("Task Declined,")) {
                String decline = message.split(",")[1];
                modifyTask(Integer.parseInt(decline), "Unauthorized", "");
            } else if (message.equals("get Reports")) {
                client.sendToClient(getAll(Reports.class));
            } else if (message.equals("get users")) {
                client.sendToClient(getAll(Users.class));
            } else if (message.equals("get notifs")) {
                client.sendToClient(getAll(NotificationMessage.class));
            } else if (message.startsWith("Change")) {
                String[] userData = message.split(",");
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Users> query = builder.createQuery(Users.class);
                    Root<Users> root = query.from(Users.class);
                    query.where(builder.equal(root.get("EmailAddress"), userData[1]));
                    List<Users> users = session.createQuery(query).getResultList();
                    if (!users.isEmpty()) {
                        for (Users user1 : users) {
                            if (user1.getEmailAddress().equals(userData[1])) {
                                user1.setPassword(passwordEncrypt(userData[2]));
                                client.sendToClient("Changed");
                            }
                        }
                    } else {
                        client.sendToClient("Don't Exist");
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
            } else if (message.startsWith("Message")) {
                String notification;
                if (message.startsWith("Message Decline")) {
                    notification = message.split(",")[3];
                } else {
                    notification = "Task was accepted";
                }
                String sender = message.split(",")[1];
                String receiver = message.split(",")[2];
                System.out.println(sender);
                System.out.println(receiver);
                LocalDateTime now = LocalDateTime.now();
                NotificationMessage notif = new NotificationMessage(1, notification, now, sender, receiver);
                add(notif);
                client.sendToClient(getAll(NotificationMessage.class));
            } else if (message.startsWith("Task Completed")) {
                LocalDateTime now = LocalDateTime.now();
                String taskID = message.split(",")[1];
                int groupID = Integer.parseInt(message.split(",")[2]);
                String sender = message.split(",")[3];
                List<Users> managerFind = getAll(Users.class);
                for (Users user : managerFind) {
                    if (user.getGroupID() == groupID && user.getTitle().equals("Manager")) {
                        NotificationMessage notif = new NotificationMessage(1, "Task" + taskID + " was completed",
                                now, sender, user.getFullName());
                        add(notif);
                    }
                }
                modifyTask(Integer.parseInt(taskID), "Task Completed", "");
            } else if (message.startsWith("TaskNotCompleted")) {
                System.out.println("working 3");
                LocalDateTime now = LocalDateTime.now();
                String taskID = message.split(",")[1];
                String user = message.split(",")[2];
                NotificationMessage notif = new NotificationMessage(1, "Required update on Task" + taskID,
                        now, "Manager", user);
                add(notif);
                client.sendToClient(getAll(NotificationMessage.class));
            } else if (message.startsWith("logOut")) {
                int userID = Integer.parseInt(message.split(",")[1]);
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Users> query = builder.createQuery(Users.class);
                    Root<Users> root = query.from(Users.class);
                    query.where(builder.equal(root.get("UserID"), userID));
                    List<Users> users = session.createQuery(query).getResultList();
                    if (!users.isEmpty()) {
                        for (Users users1 : users) {
                            users1.setActive(false);
                        }
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    public void add(Object object) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(object);
            session.flush();
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
