package si.fri.rso.samples.customers.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.samples.customers.models.dtos.Notification;
import si.fri.rso.samples.customers.models.entities.Message;
import si.fri.rso.samples.customers.models.entities.Customer;
import si.fri.rso.samples.customers.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RequestScoped
public class ChatBean {

    private Logger log = Logger.getLogger(ChatBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @Inject
    private ChatBean chatBean;


    @Inject
    private ExternalBean externalBean;

    private Client httpClient;


    @Inject
    @DiscoverService("uberapp-users")
    private Optional<String> baseUrl;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
//        baseUrl = "http://localhost:8081"; // only for demonstration
    }




    public List<Message> getChats() {

        TypedQuery<Message> query = em.createNamedQuery("Message.getAll", Message.class);
        return query.getResultList();

    }


    public List<Message> getChat(Integer userid1, Integer userid2) {

        /*
        * When userid1 and userid2 are in database then we get all messages that are bound to this two ids.
        * */

        List<Message> foundChat = new ArrayList<Message>();
        if(userid1 == userid2){
            return foundChat;
        }
        int user1exists=-1;
        int user2exists=-1;
        try {
            user1exists = externalBean.userExists(userid1);
            user2exists = externalBean.userExists(userid2);
        }catch(Exception e){
            System.out.println("error from remote service");
            System.out.println(e);
            return foundChat;
        }
        if(user1exists > 200){
            System.out.println("user 1");
            if(user1exists == 300){
                System.out.println("uporabnik ne obstaja");
            }
            if(user1exists == 400){
                System.out.println("url ne obstaja");
            }
            if(user1exists == 500){
                System.out.println("nepravilen argument v users servicu");
            }
            return foundChat;
        }
        if(user2exists > 200){
            System.out.println("user 2");
            if(user2exists == 300){
                System.out.println("uporabnik ne obstaja");
            }
            if(user2exists == 400){
                System.out.println("url ne obstaja");
            }
            if(user2exists == 500){
                System.out.println("nepravilen argument v users servicu");
            }
            return foundChat;
        }

        List<Message> chat = getChats();
        if(chat != null && chat.size() > 0){
            for(Message mes : chat){
                if((mes.getUserid1() == userid1 && mes.getUserid2()==userid2) || (mes.getUserid1() == userid2 && mes.getUserid2()==userid1)){
                    foundChat.add(mes);
                }
            }
            return foundChat;
        }else{
            return foundChat;
        }

    }

    @Metered(name="count_created_message")
    public Message createMessage(Message message) {
        if(message == null || message.getUserid1()==null || message.getUserid2() == null || message.getUserid2() == message.getUserid1()){
            System.out.println("some values was not ok");
            return null;
        }


        boolean transactionstarted = false;
        try {
            int user1exists = externalBean.userExists(message.getUserid1());
            int user2exists = externalBean.userExists(message.getUserid2());
            if(user1exists > 200 || user2exists > 200){
                System.out.println("one user does not exist");
                return null;
            }
            beginTx();
            transactionstarted = true;
            em.persist(message);
            commitTx();
        } catch (Exception e) {
            if(transactionstarted) {
                rollbackTx();
            }
        }

        return message;
    }
    /*
    public Notification editNotfication(Integer notificationId, Notification notification){
        Notification n = em.find(Notification.class, notificationId);
        if(n == null){
            return null;
        }
        try{
            beginTx();
            n.setSeen(notification.getSeen());
            n = em.merge(n);
            commitTx();
        }catch (Exception e){
            rollbackTx();
        }

        return n;
    }

    public Notification putNotification(String notificationId, Notification notification) {

        Notification c = em.find(Notification.class, notificationId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            notification.setId(c.getId());
            notification = em.merge(notification);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return notification;
    }

    public boolean deleteCustomer(String customerId) {

        Customer customer = em.find(Customer.class, customerId);

        if (customer != null) {
            try {
                beginTx();
                em.remove(customer);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }
    */

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    public void loadOrder(Integer n) {


    }

}
