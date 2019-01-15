package si.fri.rso.samples.customers.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.customers.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("main")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Log
public class MainResource {

    private Logger log = Logger.getLogger(MainResource.class.getName());

    @Inject
    private AppProperties appProperties;

    /*
    @GET
    @Path("instanceid")
    public Response getInstanceId() {

        String instanceId =
                "{\"instanceId\" : \"" + EeRuntime.getInstance().getInstanceId() + "\"}";

        return Response.ok(instanceId).build();
    }

    @POST
    @Path("healthy")
    public Response setHealth(HealthDto health) {
        appProperties.setHealthy(health.getHealthy());
        log.info("Setting health to " + health.getHealthy());
        return Response.ok().build();
    }

    @POST
    @Path("load")
    public Response loadOrder(LoadDto loadDto) {

        for (int i = 1; i <= loadDto.getN(); i++) {
            fibonacci(i);
        }

        return Response.status(Response.Status.OK).build();
    }

    */


    /*
    * Main app on port: 32432 -> v1/main/info
    * users microservice on port: 31392
    * rides microservice on port: 32511
    * */


    @GET
    @Path("info")
    public Response info() {

        JsonObject json = Json.createObjectBuilder()
                .add("clani", Json.createArrayBuilder().add("mg5331").add("ms4388"))
                .add("opis_projekta", "Aplikacija za namen iskanja in ponujanja prevozov. Ponudnik storitev ponudi svoj prevoz na neki začetki točki in sopotniki se pridružijo temu prevozu, če jih prevoznih sprejme. ")
                .add("mikrostoritve", Json.createArrayBuilder().add("http://159.122.186.123:31392/v1/users")
                        .add("http://159.122.186.123:32511/v1/rides"))
                .add("github", Json.createArrayBuilder().add("https://github.com/RSO-vaje-prevozi-2018-2019/uberapp")
                        .add("https://github.com/RSO-vaje-prevozi-2018-2019/uberapp-users")
                        .add("https://github.com/RSO-vaje-prevozi-2018-2019/uberapp-rides"))
                .add("travis", Json.createArrayBuilder().add("https://travis-ci.com/RSO-vaje-prevozi-2018-2019/uberapp")
                        .add("https://travis-ci.com/RSO-vaje-prevozi-2018-2019/uberapp-users")
                        .add("https://travis-ci.com/RSO-vaje-prevozi-2018-2019/uberapp-rides"))
                .add("dockerhub", Json.createArrayBuilder().add("https://hub.docker.com/r/glumac/uberapp/")
                        .add("https://hub.docker.com/r/glumac/uberapp-users/")
                        .add("https://hub.docker.com/r/glumac/uberapp-rides/"))
                .build();


        return Response.ok(json.toString()).build();
    }

    private long fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
