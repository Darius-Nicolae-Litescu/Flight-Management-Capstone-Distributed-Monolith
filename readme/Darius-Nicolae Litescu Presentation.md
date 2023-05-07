Capstone Project Flight management System

Darius-Nicolae Litescu

2022

Structure

1. General information
1. UML Diagrams
- 2.1 Use Case
- 2.2 Component
- 2.3 Class
- 2.4 Sequence

1\. General information

- The Flight Management System has an API that can be used to manage flights and passengers. An airline may use the system to keep track of their flights and passenger data.
- The system provides the ability to add, delete, or update flights. It also allows someone to access booking information and history. 
- Customers may use the API's search feature to discover information about flights, such as the departure and arrival cities. Customers are also able to provide comments and feedback on their trip.
- The system is based on a microservices architecture, and it is accessible via a REST API with JSON for data transmission.
1. Use Case Diagrams
- Use Case diagrams are excellent way to give a high-level overview of a system's capabilities. It can also be used to identify the key actors in a system and their interactions.

Login (User) Microservice Use Case Diagram

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.001.png)

Passenger Microservice Use Case Diagram

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.002.png)

Flight Microservice Use Case Diagram

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.003.png)

Booking Microservice Use Case Diagram

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.004.png)

Review Microservice Use Case Diagram

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.005.png)

2. Component Diagram
- The Component Diagram is used to understand how a system's individual components interact with each other.

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.006.png)

3. Class Diagrams
- Class Diagrams are used to understand a system's static structure.

Class diagram for Login and Passenger  ![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.007.jpeg)microservices 

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.008.png)

Class diagram for Flight Microservice

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.009.jpeg)

Class diagram for Booking and Review  ![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.010.jpeg)Microservices 

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.011.png)

2\.1.3 Sequence Diagrams

- Sequence Diagrams allow you to see how individual operations are executed.

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.012.jpeg)

![](Aspose.Words.53e3b14c-04ea-48d4-9f0d-d5a4e74de458.013.jpeg)
