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
    private Optional<String> url;

    @Inject
    @DiscoverService("rso-buyer")
    private Optional<String> containerUrl;

    private String queueUrl = "http://localhost:8084";
    private String orderUrl = "http://localhost:8082";
    private String buyerUrl = "http://localhost:8081";

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


    //private List<Buyer> getObjects(String json) throws IOException {
    //    return json == null ? new ArrayList<>() : objectMapper.readValue(json,
    //            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).getTypeFactory().constructCollectionType(List.class, Buyer.class));
    //}
    /*public List<Buyer> getMessageDiscovery(){
        if(url.isPresent()) {
            try {
                return httpClient
                        .target(url.get() + "/v1/buyers")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Buyer>>() {
                        });
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("errror: " + url.get() + "\t " + e.getMessage());
                //throw new InternalServerErrorException(e.getMessage());
                return null;
            }
        }
        return null;
    }*/


    public String getMessageDiscovery2(){
        if(containerUrl.isPresent()) {
            try {
                return httpClient
                        .target(containerUrl.get() + "/v1/buyers/test")
                        .request()
                        .get(String.class);
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("errror: " + containerUrl.get() + "\t " + e.getMessage());
                return "Sth went wrong!";
            }
        }
        System.out.println("errror: sth went wring!");
        return "Sth went wrong!";
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
        if(!queueUrl.isEmpty()) {
            try {
                return httpClient.target(queueUrl + "/v1/queues/" + delivererID)
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
        if(!orderUrl.isEmpty()) {
            try {
                String orderResponse = httpClient.target(orderUrl + "/v1/orders/" + orderID)
                                    .request()
                                    .accept(MediaType.APPLICATION_JSON)
                                    .get(String.class);

                JSONObject jsonOrder = new JSONObject(orderResponse);
                int buyerID = jsonOrder.getInt("buyerID");

                String buyerResponse = httpClient.target(buyerUrl + "/v1/buyers/" + buyerID)
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
}
