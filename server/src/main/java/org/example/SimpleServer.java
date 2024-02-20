package org.example;



import com.sun.net.httpserver.Request;
import org.example.ocsf.AbstractServer;
import org.example.ocsf.ConnectionToClient;
import org.example.ocsf.SubscribedClient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Param;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);
	}

	public static Session session;
	public static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Task.class);
		configuration.addAnnotatedClass(Users.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder()).applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static List<Task> getAllTasks() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Task> query = builder.createQuery(Task.class);
		query.from(Task.class);
		List<Task> data = session.createQuery(query).getResultList();
		return data;
	}

	public static void generateTasksTable() throws Exception{
		LocalDateTime now = LocalDateTime.now();
		Task task1 = new Task(1829371289,"Task1", "Walk the pets", "Eden Daddo", 2128219878, "Pending", now, "none");
		Task task2 = new Task(1829371284,"Task2","Buy medical equipment", "Leen Yakov", 1823718982, "Pending", now, "none");
		Task task3 = new Task(1829371288,"Task3","Help buy groceries", "Leen Yakov", 1823718982, "Pending", now, "none");
		Task task4 = new Task(1829371287,"Task4","Clean the house", "Karen Yakov", 2127726318, "Pending", now, "none");
		Task task5 = new Task(1829371286,"Task5","Take care of the children", "Karen Yakov", 2127726318, "Pending", now, "none");
		Task task6 = new Task(1829371285,"Task6","Give a ride", "Eden daddo", 2128219878, "Pending", now, "none");
		session.save(task1);
		session.save(task2);
		session.save(task3);
		session.save(task4);
		session.save(task5);
		session.save(task6);
		session.flush();
	}

	public static void generateUsersTable() throws Exception{
		Users user1 = new Users("Eden Daddo", 2128219878, "edenDado@gmail.com", "Haifa, Remot Remez, Haviva Reich 54",547823641, 1928312093, "User");
		Users user2 = new Users("Karen Yakov", 2127726318, "karenYakov@gmail.com", "Haifa, Remot Alon, Dovnov 18",547374388, 1928312093, "User");
		Users user3 = new Users("Leen Yakov", 1823718982, "leenYakov@gmail.com", "Haifa, Remot Alon, Dovnov 16",518723618, 1928312093, "User");
		Users user4 = new Users("Rami Benet", 2138291782, "ramiBenet@gmail.com", "Haifa, Remot Remez, Haviva Reich 60",534289782, 1928312093, "User");
		Users user5 = new Users("Abo Majd", 1212323982, "aboMajd@gmail.com", "Haifa, Remot Remez, Haviva Reich 40",541271872, 1928312093, "User");
		Users user6 = new Users("Yonatan Boris", 1728631726, "yonatanBoris@gmail.com", "Haifa, Neve Shanan, Netiv Hen 12",541782631, 1928312093, "User");
		Users user7 = new Users("Louis Litt", 1237298379, "louisLitt@gmail.com", "Haifa, Neve Shanan, Netiv Hen 10",576718722, 1928312093, "User");
		Users user8 = new Users("Yohanan Bloomfield", 1213798179, "yohananBloomfield@gmail.com", "Haifa, Neve Shanan, Hanita 24",542882281, 1928312093, "Manager");
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
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String message = (String) msg;
		Task t1 = null;
		try{
			List<Task> tasks= getAllTasks();
			for(Task task: tasks){
				if(message.startsWith("pick " + task.getTaskName())&& task.getState().equals("in progress")){
					client.sendToClient(task.getTaskName()+ " is already in progress");
				} else if(message.startsWith("pick " + task.getTaskName())){
					task.setState("in progress");
					client.sendToClient(task.getTaskName() + " was picked and now in progress");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(message);
		try{
			if(message.isBlank()){
				message = "Error, empty message";
				client.sendToClient(message);
			} else if (message.startsWith("pick task1")) {
				message = "task1 was picked";
				client.sendToClient(message);
			}
			else{
				client.sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void sendToAllClients (Task message){
		try {
			for (SubscribedClient SubscribedClient : SubscribersList) {
				SubscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
