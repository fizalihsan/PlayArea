package webservices.ch01;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 11/5/13
 * Time: 12:16 AM
 */
@Path("/helloworldresources")
public class HelloWorldResources {
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain")
    public String getPlain(@DefaultValue("Hello") @QueryParam("greeting") String greeting) {
        return greeting + " World!!!";
    }

    @GET
    @Produces({MediaType.TEXT_XML})
    @Path("/xml")
    public JAXBElement<HelloWorld> getXML() {
        return new JAXBElement<>(new QName("helloworldxml"), HelloWorld.class, new HelloWorld());
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public String getJson() throws JsonProcessingException {
        final HelloWorld helloWorld = new HelloWorld("Hi", "Buddy");
        return new ObjectMapper().writeValueAsString(helloWorld);
    }

}