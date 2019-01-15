package si.fri.rso.samples.customers.services.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import si.fri.rso.samples.customers.models.dtos.Notification;
import si.fri.rso.samples.customers.models.dtos.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ExternalBean {

    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        objectMapper = new ObjectMapper();
    }

    @Inject
    @DiscoverService(value = "uberapp-notifications")
    private Optional<String> basePathNotifications;

    @Inject
    @DiscoverService(value = "uberapp-rides")
    private Optional<String> basePathRides;



    @Inject
    @DiscoverService(value = "uberapp-users")
    private Optional<String> basePathUsers;


    public int userExists(int userid){
        /*
        * 200 = obstaja
        * 300 = user ne obstaja
        * 400 = url ne obstaja
        * 500 = neka druga napaka
        *
        * */
        String url="";
        if(basePathUsers.isPresent()){
            url = basePathUsers.get() + "/v1/users/"+userid;
        }else{
            return 400;
        }

        String json = getJSONResponse("GET", url);

        System.out.println("Dobili smo JSON "+json);
        try{
            ObjectMapper mapper = new ObjectMapper();
            User foundUser = mapper.readValue(json, User.class);
            System.out.println("user: " + foundUser);
            if(foundUser== null){
                System.out.println("Ne dobi userja");
            }else{
                System.out.println("user id:" + foundUser.getId());
                System.out.println("user name:" + foundUser.getFirstName());
            }
            if(foundUser != null && foundUser.getId() == userid){
                return 200;
            }else{
                return 300;
            }
        }catch(IOException e){
            System.out.println("Nepravilno parsanje jsona.");
            System.out.println(e);
            return 500;
        }

    }


    public List<User> getUsersFromRide(int rideId) {

        //hardcoded - todo
        List<User> users = new ArrayList<>();
        for(int i=0;i<100;i++) {
            users.add(new User(i));
        }
        return users;
    }

    public int getDriverId(int rideId) {

        String url = "";
        if (basePathRides.isPresent()) {
            url = basePathRides.get() + "/v1/rides/"+ rideId +"/driver";
        } else {
            throw new InternalServerErrorException("ni urlja");
        }
        System.out.println("url je podan, celoten: " + url);
        String json = getJSONResponse("GET", url);

        System.out.println("dobljeni json" + json);

        Integer driverId = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            driverId = mapper.readValue(json, Integer.class);
        } catch (IOException e) {
            //logiranje
            System.out.println("request: getDriverId, error: " + e);
        }
        System.out.println("vračam driverID: " + driverId);
        return driverId;
    }

    public boolean createNotification(int userId, int rideId, String text){

        JsonObject json = Json.createObjectBuilder()
                .add("userid", String.valueOf(userId))
                .add("notificationtext", text)
                .add("rideid", String.valueOf(rideId))
                .build();

        String url = "";
        if (basePathNotifications.isPresent()) {
            url = basePathNotifications.get() + "/v1/notifications/create";
        }
        String jsonStringResponse = getJSONResponse("POST", url, json.toString());

        System.out.println("dobljeni json createNotif; " + json);
        Notification notification = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            notification = mapper.readValue(jsonStringResponse, Notification.class);
        } catch (IOException e) {
            //logiranje
            System.out.println("request: createNotif, error: " + e);
        }

        return (notification != null && notification.getId() != null);

    }

    private static String getJSONResponse(String requestType, String fullUrl) {
        return getJSONResponse( requestType,  fullUrl, null);
    }

    private static String getJSONResponse(String requestType, String fullUrl, String json) {
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = null;

            if ("GET".equals(requestType)) {
                HttpGet request = new HttpGet(fullUrl);
                response = httpClient.execute(request);

            } else if ("POST".equals(requestType)) {
                HttpPost request = new HttpPost(fullUrl);

                request.setEntity(new StringEntity(json));
                request.setHeader("Content-type", "application/json");
                request.setHeader("Accept", "application/json");

                response = httpClient.execute(request);

            } else {
                throw new InternalServerErrorException("Wrong request type:" + requestType);
            }

            int status = response.getStatusLine().getStatusCode();
            System.out.println("response code: " + status);
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                    return EntityUtils.toString(entity);
            } else {
                String msg = "Remote server '" + fullUrl + "' is responded with status " + status + ".";
                System.out.println(msg);
                // todo logging
                throw new InternalServerErrorException(msg);
            }

        } catch (IOException e) {
            String msg = e.getClass().getName() + " occured: " + e.getMessage();
            // todo logging
            System.out.println(msg);
            throw new InternalServerErrorException(msg);
        }
        return "{}"; //empty json
    }

//    private List<AccountOptions> getObjects(String json) throws IOException {
//        return json == null ? new ArrayList<>() : objectMapper.readValue(json,
//                objectMapper.getTypeFactory().constructCollectionType(List.class, AccountOptions.class));
//    }
}
