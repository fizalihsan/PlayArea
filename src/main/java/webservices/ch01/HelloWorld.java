package webservices.ch01;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 11/5/13
 * Time: 1:10 AM
 */
@XmlRootElement(name = "greet")
public class HelloWorld {
    private String greeting = "Hello";
    private String receiver = "World";

    public HelloWorld() {
    }

    public HelloWorld(String greeting, String receiver) {
        this.greeting = greeting;
        this.receiver = receiver;
    }

    public String getGreeting() { return greeting; }

    public void setGreeting(String greeting) { this.greeting = greeting; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    @Override
    public String toString() {
        return greeting + " " + receiver + "!!!";
    }
}
