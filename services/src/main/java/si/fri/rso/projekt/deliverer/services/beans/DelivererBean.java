package si.fri.rso.projekt.deliverer.services.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
//import si.fri.rso.projekt.buyers.models.Buyer;
import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.projekt.deliverer.services.configuration.AppProperties;
import si.fri.rso.projekt.deliverer.models.MongoDeliverer;
import si.fri.rso.projekt.deliverer.models.Deliverer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class DelivererBean {

    private Logger log = Logger.getLogger(DelivererBean.class.getName());

    private Client httpClient;

    private ObjectMapper objectMapper;

    @Inject
    private AppProperties appProperties;

    @Inject
    @DiscoverService("rso-queue")
    private Optional<String> baseUrlQueue;

    @Inject
    @DiscoverService("rso-buyer")
    private Optional<String> baseUrlBuyer;

    @Inject
    @DiscoverService("rso-order")
    private Optional<String> baseUrlOrder;

    @Inject
    @DiscoverService("rso-bill")
    private Optional<String> baseUrlBill;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        objectMapper = new ObjectMapper();
    }

    public String readConfig() {
        if (appProperties.isExternalServicesEnabled())
            return "ext service enabled!";
        return "ext service disabled";
    }


    public void setConfig(boolean config) {
        appProperties.setExternalServicesEnabled(config);
    }


    public List<Deliverer> getDeliverers() {
        MongoDeliverer mb = new MongoDeliverer();

        return mb.getAllDeliverers();
    }

    public Deliverer getDeliverer(Integer delivererID) {
        MongoDeliverer mb = new MongoDeliverer();

        Deliverer deliverer = mb.getDeliverer(delivererID);

        if(delivererID == null) {
            return null;
        }

        return deliverer;
    }

    public void createDeliverer(JSONObject json) {
        MongoDeliverer md = new MongoDeliverer();

        md.createDeliverer(json);
    }

    public void deleteDeliverer(int delivererID) {
        MongoDeliverer mb = new MongoDeliverer();

        mb.deleteDeliverer(delivererID);
    }

    public String getOrdersByDelivererID(int delivererID){
        if(baseUrlQueue.isPresent()) {
            try {
                return httpClient.target(baseUrlQueue.get() + "/v1/queues/" + delivererID)
                        .request()
                        .accept(MediaType.APPLICATION_JSON)
                        .get(String.class);
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("wrong");
            }
        }
        System.out.println("errror: sth went wring!");
        return "Sth went wrong!";
    }

    public String getAddressByOrderID(int orderID) {
        if(baseUrlOrder.isPresent() && baseUrlBuyer.isPresent()) {
            try {
                String orderResponse = httpClient.target(baseUrlOrder.get() + "/v1/orders/" + orderID)
                                    .request()
                                    .accept(MediaType.APPLICATION_JSON)
                                    .get(String.class);

                JSONObject jsonOrder = new JSONObject(orderResponse);
                int buyerID = jsonOrder.getInt("buyerID");

                String buyerResponse = httpClient.target(baseUrlBuyer.get() + "/v1/buyers/" + buyerID)
                                    .request()
                                    .accept(MediaType.APPLICATION_JSON)
                                    .get(String.class);

                JSONObject jsonBuyer = new JSONObject(buyerResponse);
                JSONObject jsonAddress = jsonBuyer.getJSONObject("address");

                return jsonAddress.toString();
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("wrong");
            }
        }
        System.out.println("errror: sth went wring!");
        return "Sth went wrong!";
    }

    public Boolean getPaidStatusByOrderID(int orderID){
        if(baseUrlBill.isPresent()) {
            try {
                return httpClient.target(baseUrlBill.get() + "/v1/bills/paid/" + orderID)
                        .request()
                        .accept(MediaType.APPLICATION_JSON)
                        .get(Boolean.class);
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("wrong");
            }
        }
        System.out.println("errror: sth went wring!");
        return null;
    }

    public Boolean setPaidStatusByOrderID(int orderID){
        if(baseUrlBill.isPresent()) {
            try {
                return httpClient.target(baseUrlBill.get() + "/v1/bills/setPaid/" + orderID)
                        .request()
                        .accept(MediaType.APPLICATION_JSON)
                        .get(Boolean.class);
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("wrong");
            }
        }
        System.out.println("errror: sth went wring!");
        return null;
    }
}
