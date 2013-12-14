package webservices.ch02;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 11/5/13
 * Time: 12:16 AM
 */
@Path("/helloworldresources2")
public class HelloWorldResources {
    //using a thread-safe list here to handle concurrent requests.
    //declared static since the a new instance of HelloWorldResources is created for every request.
    private static List<HelloWorld> greetings = new CopyOnWriteArrayList<>();

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain")
    public String getPlain() {
        StringBuilder builder = new StringBuilder();
        for (HelloWorld greeting : greetings) {
            builder.append(greeting);
        }
        return builder.toString();
    }

    @GET
    @Produces({MediaType.TEXT_XML})
    @Path("/xml")
    public List<JAXBElement<HelloWorld>> getXML() {
        List<JAXBElement<HelloWorld>> xmlElements = new ArrayList<>();
        for (HelloWorld greeting : greetings) {
            xmlElements.add(new JAXBElement<>(new QName("helloworldxml"), HelloWorld.class, greeting));
        }
        return xmlElements;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public String getJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(greetings);
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response addGreeting( @FormParam("greeting") String greeting, @FormParam("receiver") String receiver ) {
        if (greeting == null || receiver == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Please pass in a greeting with a receiver !!!\n").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }

        HelloWorld helloWorld = new HelloWorld(greeting, receiver);
        greetings.add(helloWorld);
        return Response.ok("Successfully added: " + helloWorld, "text/plain").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Path("/query")
    public HelloWorld queryGreeting(@QueryParam("id") int id) {
        HelloWorld result = null;
        for (HelloWorld helloWorld : greetings) { //doing linear search for simplicity
            if (helloWorld.getId() == id) {
                result = helloWorld;
                break;
            }
        }
        return result;
    }

}