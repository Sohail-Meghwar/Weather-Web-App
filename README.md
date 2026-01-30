Weather Web App (Java, JSP & Servlet):

Description :

This is a simple Weather Web Application built using Java, JSP, and Servlets, designed for absolute beginners who want to learn Java web application development.
The app allows users to enter a city name and fetch its current weather information. It demonstrates how JSP pages interact with Servlets to handle user requests, process data, and display dynamic content.

The project avoids heavy frameworks and focuses on core Java web concepts, making it ideal for students and beginners who are new to backend development with Java.

Features:

Search weather by city name
Simple and user-friendly interface
Uses JSP for frontend rendering
Uses Servlets for request handling
Beginner-level MVC structure
Easy to understand and extend

Technologies Used :

Java
JSP (JavaServer Pages)
Servlets
HTML & CSS
Apache Tomcat Server

Project Structure :

Weather-App/
│
├── src/
│   └── com.weather.servlet
│       └── WeatherServlet.java
│
├── WebContent/
│   ├── index.jsp
│   ├── result.jsp
│   └── css/
│       └── style.css
│
└── web.xml

How It Works :

The user enters a city name on the homepage.
The request is sent to a Servlet.
The Servlet processes the request and fetches weather data.
The result is forwarded to a JSP page.
Weather information is displayed to the user.

How to Run the Project
Clone the repository

git clone https://github.com/your-username/weather-app.git
Open the project in Eclipse or NetBeans.

Configure Apache Tomcat server.

Run the project on the server.

Open your browser and go to:

http://localhost:8080/Weather-App/

Who Is This Project For?

Absolute beginners in Java
Students learning JSP and Servlets
Anyone starting Java web development
Practice project for interviews or resumes

Future Improvements :

Add real-time weather API integration
Improve UI design
Add error handling for invalid city names
Show additional weather details like humidity and wind speed

Author :
Sohail Meghwar Java Developer | Beginner-friendly Projects
