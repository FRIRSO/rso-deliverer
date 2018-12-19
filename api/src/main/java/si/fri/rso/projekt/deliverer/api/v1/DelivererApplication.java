package si.fri.rso.projekt.deliverer.api.v1;

import com.kumuluz.ee.discovery.annotations.RegisterService;
//import org.glassfish.jersey.process.internal.RequestScoped;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService
@ApplicationPath("v1")
public class DelivererApplication extends Application {

}
