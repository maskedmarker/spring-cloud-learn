# 关于spring-cloud的概述

## overview
Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems 
(e.g. configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, short lived microservices and contract testing).
Coordination of distributed systems leads to boiler plate patterns, and using Spring Cloud developers can quickly stand up services and applications that implement those patterns.

Features
Spring Cloud focuses on providing good out of box experience for typical use cases and extensibility mechanism to cover others.
Distributed/versioned configuration
Service registration and discovery
Routing
Service-to-service calls
Load balancing
Circuit Breakers
Distributed messaging
Short lived microservices (tasks)
Consumer-driven and producer-driven contract testing

### springCloud与springBoot的版本
2021.0.x aka Jubilee	2.6.x, 2.7.x (Starting with 2021.0.3)
2020.0.x aka Ilford	    2.4.x, 2.5.x (Starting with 2020.0.3)
Hoxton	                2.2.x, 2.3.x (Starting with SR5)
Greenwich	            2.1.x
Finchley	            2.0.x
Edgware	                1.5.x
Dalston	                1.5.x

Spring Cloud Dalston, Edgware, Finchley, Greenwich, 2020.0 (aka Ilford), 2021.0 (aka Jubilee) have all reached end of life status and are no longer supported.

### 依赖版本管理
It is recommended that you use release train BOM spring-cloud-dependencies 
This is a BOM-only version and it just contains dependency management and no plugin declarations or direct references to Spring or Spring Boot. 
You can Spring Boot parent POM, or use the BOM from Spring Boot (spring-boot-dependencies) to manage Spring Boot versions.


## cloud-native
Cloud Native is a style of application development that encourages easy adoption of best practices in the areas of continuous delivery and value-driven development.
云原生是一种应用程序开发风格，鼓励在持续交付和价值驱动开发领域轻松采用最佳实践。

Spring Cloud facilitates these styles of development in a number of specific ways. The starting point is a set of features to which all components in a distributed system need easy access.
Many of those features are covered by Spring Boot, on which Spring Cloud builds. 
Some more features are delivered by Spring Cloud as two libraries: Spring Cloud Context and Spring Cloud Commons. 
Spring Cloud Context provides utilities and special services for the ApplicationContext of a Spring Cloud application (bootstrap context, encryption, refresh scope, and environment endpoints). 
Spring Cloud Commons is a set of abstractions and common classes used in different Spring Cloud implementations (such as Spring Cloud Netflix and Spring Cloud Consul).

## Spring Cloud Context: Application Context Services

### The Bootstrap Application Context
A Spring Cloud application operates(运行) by creating a “bootstrap” context, which is a parent context for the main application.
This context is responsible for loading configuration properties from the external sources and for decrypting properties in the local external configuration files.
The two contexts share an Environment, which is the source of external properties for any Spring application. 
By default, bootstrap properties (not bootstrap.properties but properties that are loaded during the bootstrap phase) are added with high precedence, so they cannot be overridden by local configuration.

### Application Context Hierarchies
If you build an application context from SpringApplication or SpringApplicationBuilder, the Bootstrap context is added as a parent to that context. 
It is a feature of Spring that child contexts inherit property sources and profiles from their parent, so the “main” application context contains additional property sources, compared to building the same context without Spring Cloud Config.
备注:context被分为bootstrap context(spring-cloud层面)和main context(应用层面)

The additional property sources are:
“bootstrap”: If any PropertySourceLocators are found in the bootstrap context and if they have non-empty properties, an optional CompositePropertySource appears with high priority. 
    An example would be properties from the Spring Cloud Config Server.
    See “Customizing the Bootstrap Property Sources” for how to customize the contents of this property source.
“applicationConfig: [classpath:bootstrap.yml]” (and related files if Spring profiles are active): If you have a bootstrap.yml (or .properties), those properties are used to configure the bootstrap context. 
    Then they get added to the child context when its parent is set. 
    They have lower precedence than the application.yml (or .properties) and any other property sources that are added to the child as a normal part of the process of creating a Spring Boot application. 
    See “Changing the Location of Bootstrap Properties” for how to customize the contents of these property sources.

Because of the ordering rules of property sources, the “bootstrap” entries take precedence. 
However, note that these do not contain any data from bootstrap.yml, which has very low precedence but can be used to set defaults.

You can extend the context hierarchy by setting the parent context of any ApplicationContext you create--
for example, by using its own interface or with the SpringApplicationBuilder convenience methods (parent(), child() and sibling()). 
The bootstrap context is the parent of the most senior ancestor that you create yourself. 

Note that the SpringApplicationBuilder lets you share an Environment amongst the whole hierarchy, but that is not the default. 
Thus, sibling contexts (in particular) do not need to have the same profiles or property sources, even though they may share common values with their parent.

### Customizing the Bootstrap Configuration
The bootstrap context can be set to do anything you like by adding entries to /META-INF/spring.factories under a key named org.springframework.cloud.bootstrap.BootstrapConfiguration. 
This holds a comma-separated list of Spring @Configuration classes that are used to create the context. 
Any beans that you want to be available to the main application context for autowiring can be created here. 
There is a special contract for @Beans of type ApplicationContextInitializer. 

When adding custom BootstrapConfiguration, be careful that the classes you add are not @ComponentScanned by mistake into your “main” application context, where they might not be needed. 
Use a separate package name for boot configuration classes and make sure that name is not already covered by your @ComponentScan or @SpringBootApplication annotated configuration classes.
备注:需要通过spring.factories加载的@Configuration classes,主要不要同时被@ComponentScan到.

The bootstrap process ends by injecting initializers into the main SpringApplication instance (which is the normal Spring Boot startup sequence, whether it runs as a standalone application or is deployed in an application server). 
First, a bootstrap context is created from the classes found in spring.factories. 
Then, all @Beans of type ApplicationContextInitializer are added to the main SpringApplication before it is started.


## Spring Cloud Commons: Common Abstractions

Patterns such as service discovery, load balancing, and circuit breakers lend themselves to a common abstraction layer that can be consumed by all Spring Cloud clients, independent of the implementation.