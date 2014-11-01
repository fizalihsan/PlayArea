| General API | Point-to-Point API | Pub-Sub API | 
| ----------- | ------------------ | ----------- | 
| ConnectionFactory| QueueConnectionFactory | TopicConnectionFactory | 
| Destination| Queue| Topic |
| Connection| QueueConnection| TopicConnection | 
| Session| QueueSession| TopicSession | 
| Message| Message| Message | 
| MessageProducer| QueueSender| TopicPublisher | 
| MessageConsumer| QueueReceiver| TopicSubscriber |


# SOA

## Message Exchange Patterns (MEP)

* An MEP is a generic interaction pattern that defines the message exchange between two services
* MEPs can composed to support the creation of large, more complex patterns.

### Primitive MEPs
1. Request-response
  * this could be synchronous or asynchronous
  * typically a "correlation" is used to associate a response with the request 
2. Fire-and-forget
  * This simple asynchronous pattern is based on a *unidirectional* transmission of messages from a source to one or more destinations. 
  * There are few variations in this MEP.
    * *single-destination* pattern - a source sends a message to one destination only
    * *multi-cast* pattern - a source sends messages to a predefined set of destinations
    * *broadcast* pattern - same as multi-cast pattern, except that the message is sent out to a broader range of recipient destinations

### Complex MEPs

* Example of a complex MEP is **Publish-and-subscribe** model

### MEPs and WSDL

#### WSDL 1.1 Spec

1. **Request-response operation** - upon receiving a message, the service must response with a standard message or a fault message
2. **Solicit-response operation** - upon submitting a message to a service requestor, the service expects a standard response message or a fault message
3. **One-way operation** - the service expects a single message and is not obligated to respond.
4. **Notification operation** - The service sends a message and expects no response.

#### WSDL 2.0 Spec

1. **in-out** pattern - equivalent to WSDL 1.1 request-response
2. **out-in** pattern - equivalent to WSDL 1.1 solicit-response
3. **in-only** pattern - equivalent to WSDL 1.1 one-way
4. **out-only** pattern - equivalent to WSDL 1.1 notification
5. **robust in-only** pattern - a variation of the in-only pattern that provides the option of launching a fault response message as a result of a transmission or processing error
6. **robust out-only** pattern - like out-only pattern, has an outbound message initiating the transmission. the difference here is that a fault message can be issued in response to the receipt of this message
7. **in-optional-out** pattern - similar to in-out with an exception. This variation introduces a rule stating that the delivery of a response message is optional and should therefore not be expected by the service requetor that originated the communication. This pattern also supports the generation of a fault message.
8. **out-optional-in** pattern - reverse of the in-optional-out, where the incoming message is optional. Fault message generation is also supported.
