import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String apiKey = "ff8f1ada713affaf19c503b1df238c6e";
        String city = request.getParameter("city"); 
        
        // Validate city parameter
        if (city == null || city.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a city name");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // Get the response code FIRST
            int responseCode = connection.getResponseCode();
            
            // Check if city was found (response code 200 = OK)
            if (responseCode == 200) {
                // Success - read the response
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                Scanner scanner = new Scanner(reader);
                StringBuilder responseContent = new StringBuilder();

                while (scanner.hasNext()) {
                    responseContent.append(scanner.nextLine());
                }
                
                scanner.close();
                
                // Parse the JSON response
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
                
                // Check if the response contains an error message
                if (jsonObject.has("cod") && !jsonObject.get("cod").getAsString().equals("200")) {
                    String errorMessage = jsonObject.has("message") 
                            ? jsonObject.get("message").getAsString() 
                            : "City not found";
                    request.setAttribute("error", "Error: " + errorMessage);
                    request.setAttribute("city", city);
                } else {
                    // Extract weather data
                    long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
                    String date = new Date(dateTimestamp).toString();
                    
                    double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                    int temperatureCelsius = (int) (temperatureKelvin - 273.15);
                   
                    int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                    
                    double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                    
                    String weatherCondition = jsonObject.getAsJsonArray("weather")
                            .get(0).getAsJsonObject().get("main").getAsString();
                    
                    // Set the data as request attributes
                    request.setAttribute("date", date);
                    request.setAttribute("city", city);
                    request.setAttribute("temperature", temperatureCelsius);
                    request.setAttribute("weatherCondition", weatherCondition); 
                    request.setAttribute("humidity", humidity);    
                    request.setAttribute("windSpeed", windSpeed);
                    request.setAttribute("weatherData", responseContent.toString());
                }
                
            } else if (responseCode == 404) {
                // City not found
                request.setAttribute("error", "City '" + city + "' not found. Please check the spelling.");
                request.setAttribute("city", city);
            } else {
                // Other API error
                request.setAttribute("error", "API Error: Unable to fetch weather data (Code: " + responseCode + ")");
                request.setAttribute("city", city);
            }
            
            connection.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Connection Error: " + e.getMessage());
            request.setAttribute("city", city);
        }

        // Forward to the JSP page
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}