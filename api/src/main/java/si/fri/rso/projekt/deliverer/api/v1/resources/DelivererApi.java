package si.fri.rso.projekt.deliverer.api.v1.resources;

import si.fri.rso.projekt.deliverer.services.beans.DelivererBean;
import si.fri.rso.projekt.deliverer.models.Deliverer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("deliverers")
public class DelivererApi {

    @Inject
    private DelivererBean delivererBean;


    //@GET
    //@Path("url")
    //public Response test() {
    //    return Response.status(Response.Status.OK).entity(delivererBean.getMessageDiscovery()).build();
    //
    //}

    @GET
    @Path("url2")
    public Response test2() {
        return Response.status(Response.Status.OK).entity(delivererBean.getMessageDiscovery2()).build();

    }

    @GET
    @Path("service")
    public Response service() {
        return Response.status(Response.Status.OK).entity(delivererBean.readConfig()).build();
    }

    @GET
    @Path("disable")
    public Response test4() {
        delivererBean.setConfig(false);
        String response = "OK";
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("enable")
    public Response test5() {
        delivererBean.setConfig(true);
        String response = "OK";
        return Response.status(Response.Status.OK).entity(response).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        return Response.ok(delivererBean.getDeliverers()).build();
    }

    @GET
    @Path("/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersbyID(@PathParam("orderID") Integer delivererID) {
        Deliverer deliverer = delivererBean.getDeliverer(delivererID);

        if(deliverer != null) {
            return Response.ok(deliverer).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
