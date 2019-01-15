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
    @Metered(name="count_get_chat")
    public Response getChat(@PathParam("user1id") Integer user1id, @PathParam("user2id") Integer user2id) {
        try {
            List<Message> chat = chatBean.getChat(user1id,user2id);
            System.out.println("your in a barbie time");
            return Response.status(Response.Status.OK).entity(chat).build();
        }catch (Exception e){
            System.out.println("error");
            System.out.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("create")
    @Metered(name="count_created_messages")
    public Response createMessage(Message msg){
        try{
            System.out.println("does work");
            Message createdMsg = chatBean.createMessage(msg);
            if(createdMsg == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }else{
                return Response.status(Response.Status.OK).entity(createdMsg).build();
            }
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
