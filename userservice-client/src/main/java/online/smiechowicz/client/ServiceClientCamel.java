package online.smiechowicz.client;

import online.smiechowicz.domain.client.ServiceClient;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class ServiceClientCamel implements ServiceClient {

    private final ProducerTemplate template;

    public ServiceClientCamel(ProducerTemplate template, CamelContext context, ServiceClientConfig clientConfig) {
        new ServiceClientRoutes(clientConfig, context).initialize();
        this.template =template;
    }

    @Override
    public String sayHello() {
        return null;
    }

    @Override
    public String sayHello(String name) {
        return null;
    }
}
