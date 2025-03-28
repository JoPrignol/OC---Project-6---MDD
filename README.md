# MDD

![Static Badge](https://img.shields.io/badge/TypeScript-4.7.2-blue) ![Static Badge](https://img.shields.io/badge/Angular-14.1.0-purple) ![Static Badge](https://img.shields.io/badge/Tailwind_CSS-3.4.17-skyblue) ![Static Badge](https://img.shields.io/badge/Angular_Material-14.2.5-violet)
![Static Badge](https://img.shields.io/badge/Java-11-green) ![Static Badge](https://img.shields.io/badge/Spring_Boot-2.7.3-green) ![Static Badge](https://img.shields.io/badge/MySQL-yellow) ![Static Badge](https://img.shields.io/badge/Maven-orange) 
## Project description

MDD is a social network for developers. Everyone can publish new articles and tie them to a subject. 
People can follow several subjects to get all the related articles. There's also a possibility to comment articles. 

## Prerequisites  

Before starting, make sure you have the following : 

- Node.js
- Angular CLI
-  npm
-  Java 11 
-  Maven 
-  MySQL
- 
## Project structure 

-   `front/`: Contains the Angular front-end application.
-   `back/`: Contains the Spring Boot back-end application.

## Front-end

Before you start, don't forget to install your node_modules with

    npm install

### Development server

To start a dev server, run 

    `ng serve`

Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.


## Back-end

### Installation & Setup

Install all required dependencies with:

``` mvn clean install ```

#### DB 

Create a database and link it to the project. A DB schema is available in:
>src/resources/script.sql

This schema will provide you test data to let you explore the project. 

#### Run the app

Run the app with:

```mvn spring-boot:run ```
