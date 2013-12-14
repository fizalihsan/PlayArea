package webservices.ch02;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 11/5/13
 * Time: 1:10 AM
 */
@XmlRootElement(name = "greet")
public class HelloWorld {
    private static AtomicInteger uniqueId = new AtomicInteger(1);
    private Integer id;
    private String greeting;
    private String receiver;

    public HelloWorld() {
        this("Hello", "World");
    }

    public HelloWorld(String greeting, String receiver) {
        this.id = uniqueId.getAndIncrement();
        this.greeting = greeting;
        this.receiver = receiver;
    }

    public Integer getId() { return id; }
    public String getGreeting() { return greeting; }
    public void setGreeting(String greeting) { this.greeting = greeting; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    @Override
    public String toString() {
        return id + ": " + greeting + " " + receiver + " !!!\n";
    }
}
