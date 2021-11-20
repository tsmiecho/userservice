package online.smiechowicz.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;

@Slf4j
public class ServiceClientRoutes {

    public static final String HELLO_ROUTE = "direct:greetingRoute";
    public static final String HELLO_TO_ROUTE = "direct:greetingToRoute";
    private final ServiceClientConfig clientConfig;
    private final CamelContext context;

    ServiceClientRoutes(ServiceClientConfig clientConfig, CamelContext context) {
        this.clientConfig = clientConfig;
        this.context = context;
    }

    void initialize() {
        try {
            context.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        onException(HttpOperationFailedException.class)
                                .redeliveryDelay(clientConfig.getRetryDelay())
                                .maximumRedeliveries(clientConfig.getRetries())
                                .useExponentialBackOff()
                                .end();
//                        from(HELLO_ROUTE)
//                                .setHeader(Exchange.HTTP_PATH, constant(PathConstants.V1_SAY_HELLO))
//                                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
//                                .to("micrometer:timer:com.idemia.did.client.service.timer?action=start")
//                                .to(clientConfig.getUrl())
//                                .to("micrometer:timer:com.idemia.did.client.service.timer?action=stop");
//                        from(HELLO_TO_ROUTE)
//                                .setHeader(Exchange.HTTP_PATH, constant(PathConstants.V1_SAY_HELLO))
//                                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
//                                .to("micrometer:timer:com.idemia.did.client.service.timer?action=start")
//                                .to(clientConfig.getUrl())
//                                .to("micrometer:timer:com.idemia.did.client.service.timer?action=stop");
                    }
                });
        } catch (Exception e) {
            log.error("Failure adding routes.", e);
        }
    }
}
