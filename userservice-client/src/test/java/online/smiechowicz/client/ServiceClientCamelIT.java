package online.smiechowicz.client;

import online.smiechowicz.domain.client.ServiceClient;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class ServiceClientCamelIT extends CamelTestSupport {

    private ServiceClient cut;

    @Before
    public void init() {
        ServiceClientConfig config = ServiceClientConfig.builder().url("http://localhost:8080").build();
        this.cut = new ServiceClientCamel(template, context, config);
    }

    @Test
    public void testGreeting() {
        String result = cut.sayHello();
        assertEquals("Hello World", result);
    }

    @Test
    public void testGreetingTo() {
        String result = cut.sayHello("Dolly");
        assertEquals("Hello Dolly", result);
    }

}
