# NFT_web_project
Cybersecurity NFT shop project

# Prerequisites
### Recommended IDE
  - IntelliJ IDEA Ultimate

### Technology used
- Java 
  - Servlet 5.0
  - JRE 17.0
- Database
  - SQLite 3.39
- Server
  - Servlet container - Tomcat 9 
- Build tools
  - Maven
- Front-end
  - JSP
  - Bootstrap


### Architectural Design
#### MVC
This project make use of MVC architecture with custom implementation to suit the project.

- **Model**
  - Every entity in the project is a model
  - Model DOES NOT contain business logic
  - Model is also used as DTO
- **View**
  - JSP files
  - Some views are served statically
  - Dynamic views are served through the controller layer
- **Controller**
  - Java Servlets
    - The controller layer that manages the flow of traffic
    - Act as a primary gateway to the backend/database
    - View layer can delegate business logic to this layer
  - Service Layer
    - Act as a bridge between the controller (Java servlets) and the DAO
    - Deals with input validation and sanitization