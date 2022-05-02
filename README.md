# Earthquake Records
It is an application to get the Earthquake Records from past X days.

## Table of Contents
- General Info
- Technologies
- Setup
- Further Improvements

### General Info
The application is designed as a fully functional web application 
including front-end development using Spring Boot, React, and an
external API.

### Technologies
- Spring Boot
- Spring Web
- Java 8
- JavaScript
- Maven
- React
- Axios
- Material UI

### Setup
To run the project,
- Clone/Download it,
- Install Maven dependencies,
- Run Main method to start the backend application,
- Using terminal, switch to the frontend folder and run "**npm -i**" command,
- Lastly run "**npm start**" command to run the frontend application,
- The application will be available on localhost:3000

### Further Improvements
The project is fully functional, however some further improvements are required:
- To overcome Cross-Origin Request Block, config file added, however
it is not recommended solving it in this way in a live product. It is
only applicable since the project is designed only to run locally for now.
- API constraints the application too much. There is no way to query
the data using a country name. Thus, it is not running fast enough. Due to this,
maximum days that can be searched is limited to 30 days.
- USA states are specified only with their state name in the API, but
there is no indication of the country name(USA). Therefore, USA related queries are done separately.
- Some earthquakes do not belong to any country, so they are ignored.
- UI can be improved, but it is not the main scope of the project.

