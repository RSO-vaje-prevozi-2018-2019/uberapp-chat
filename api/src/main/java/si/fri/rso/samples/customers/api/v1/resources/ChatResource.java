package si.fri.rso.samples.customers.api.v1.resources;


import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.samples.customers.models.entities.Message;
import si.fri.rso.samples.customers.services.beans.ChatBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatResource {

    @Inject
    private ChatBean chatBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/{user1id}/{user2id}")
    @Metered(name="count_get_all_notifications")
    public Response getChat(@PathParam("user1id") Integer user1id, @PathParam("user2id") Integer user2id) {
        try {
            List<Message> notifications = chatBean.getChat(user1id,user2id);

            return Response.ok(notifications).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
