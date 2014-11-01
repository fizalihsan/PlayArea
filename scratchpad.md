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

1. Request-response operation - upon receiving a message, the service must response with a standard message or a fault message
2. Solicit-response operation - upon submitting a message to a service requestor, the service expects a standard response message or a fault message
3. One-way operation - the service expects a single message and is not obligated to respond.
4. Notification operation - The service sends a message and expects no response.

#### WSDL 2.0 Spec

1. 
