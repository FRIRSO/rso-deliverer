package si.fri.rso.projekt.deliverer.services.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

//dev->1.0->config->app-properties->external-services-> enabled : true / false
@ConfigBundle("app-properties")
public class AppProperties {
    @ConfigValue(value = "external-services.enabled", watch = true)
    private boolean externalServicesEnabled;

    @ConfigValue(value = "external-services.nameOne", watch = true)
    private String externalServicesNameOne;

    @ConfigValue(value = "external-services.nameTwo", watch = true)
    private String externalServicesNameTwo;

    public String externalServicesNameOne() {
        return externalServicesNameOne;
    }

    public void externalServicesNameOne(String externalServicesNameOne) {
        this.externalServicesNameOne = externalServicesNameOne;
    }

    public String externalServicesNameTwo() {
        return externalServicesNameTwo;
    }

    public void externalServicesNameTwo(String externalServicesNameTwo) {
        this.externalServicesNameTwo = externalServicesNameTwo;
    }

    public boolean isExternalServicesEnabled() {
        return externalServicesEnabled;
    }

    public void setExternalServicesEnabled(boolean externalServicesEnabled) {
        this.externalServicesEnabled = externalServicesEnabled;
    }
}
