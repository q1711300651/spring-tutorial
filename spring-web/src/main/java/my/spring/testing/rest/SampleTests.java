package my.spring.testing.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 * Остальные примеры по ссылке:
 * https://github.com/spring-projects/spring-framework/tree/master/spring-test/src/test/java/org/springframework/test/web/client/samples
 *
 * Examples to demonstrate writing client-side REST tests with Spring MVC Test.
 * While the tests in this class invoke the RestTemplate directly, in actual
 * tests the RestTemplate may likely be invoked indirectly, i.e. through client
 * code.
 *
 * @author Rossen Stoyanchev
 */
public class SampleTests {

    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.mockServer = MockRestServiceServer.createServer(this.restTemplate);
    }


    @Test
    public void performGet() throws Exception {
        String responseBody = "{\"name\" : \"Ludwig van Beethoven\", \"someDouble\" : \"1.6035\"}";

        this.mockServer.expect(requestTo("/composers/42")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        //@SuppressWarnings("unused")
        //Person ludwig = restTemplate.getForObject("/composers/{id}", Person.class, 42);

        // We are only validating the request. The response is mocked out.
        // hotel.getId() == 42
        // hotel.getName().equals("Holiday Inn")
        this.mockServer.verify();
    }
    @Test
    public void performGetWithResponseBodyFromFile() throws Exception {
        Resource responseBody = new ClassPathResource("ludwig.json", this.getClass());
        this.mockServer.expect(requestTo("/composers/42")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));
        @SuppressWarnings("unused")
        Person ludwig = restTemplate.getForObject("/composers/{id}", Person.class, 42);
        // hotel.getId() == 42
        // hotel.getName().equals("Holiday Inn")
        this.mockServer.verify();
    }


    @Test
    public void verify() {
        this.mockServer.expect(requestTo("/number")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("1", MediaType.TEXT_PLAIN));
        this.mockServer.expect(requestTo("/number")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("2", MediaType.TEXT_PLAIN));
        this.mockServer.expect(requestTo("/number")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("4", MediaType.TEXT_PLAIN));
        this.mockServer.expect(requestTo("/number")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("8", MediaType.TEXT_PLAIN));
        @SuppressWarnings("unused")
        String result = this.restTemplate.getForObject("/number", String.class);
        // result == "1"
        result = this.restTemplate.getForObject("/number", String.class);
        // result == "2"
        try {
            this.mockServer.verify();
        }
        catch (AssertionError error) {
            assertTrue(error.getMessage(), error.getMessage().contains("2 out of 4 were executed"));
        }
    }
}