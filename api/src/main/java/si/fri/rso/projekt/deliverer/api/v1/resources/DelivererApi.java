package si.fri.rso.projekt.deliverer.api.v1.resources;

import org.json.JSONObject;
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

    @POST
    public Response createDeliverer(String deliverer) {
        delivererBean.createDeliverer(new JSONObject(deliverer));

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/delete/{delivererID}")
    public Response deleteBuyer(@PathParam("delivererID") Integer delivererID) {
        delivererBean.deleteDeliverer(delivererID);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/orders/{delivererID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delivererOrders(@PathParam("delivererID") Integer delivererID) {
        return Response.ok(delivererBean.getOrdersByDelivererID(delivererID)).build();
    }

    @GET
    @Path("/address/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delivererAddress(@PathParam("orderID") Integer orderID) {
        return Response.ok(delivererBean.getAddressByOrderID(orderID)).build();
    }

    @GET
    @Path("/paid/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isOrderPaid(@PathParam("orderID") Integer orderID) {
        return Response.ok(delivererBean.getPaidStatusByOrderID(orderID)).build();
    }

    @GET
    @Path("/setPaid/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setOrderPaid(@PathParam("orderID") Integer orderID) {
        return Response.ok(delivererBean.setPaidStatusByOrderID(orderID)).build();
    }
}
