import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class WeatherApp {
    private static Scanner scanner = new Scanner(System.in);
    private static String apiKey = "4f808ef3351bb0c5aa6a3c85a7170ed8";
    private static String city;

    public static void main(String[] args) throws IOException {
        while (true){
            System.out.print("Masukkan Lokasi yang Ingin Anda Ketahui Cuacanya (0 untuk selesai): ");
            city = scanner.nextLine();
            if (city.equals("0")){
                System.out.println("See you :)");
                System.exit(0);
            }
            String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Location not found");
                System.out.println("========================================================================");
            } else if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                String jsonStr = "";
                while ((output = br.readLine()) != null) {
                    jsonStr += output;
                }
                conn.disconnect();
                JSONObject jsonObj = new JSONObject(jsonStr);
                String weatherDesc = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description");
                double tempKelvin = jsonObj.getJSONObject("main").getDouble("temp");
                double tempCelcius = tempKelvin - 273.15;
                int humidity = jsonObj.getJSONObject("main").getInt("humidity");
                System.out.println("Weather in " + city + ": " + weatherDesc);
                System.out.println("Temperature: " + tempCelcius + " °C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("========================================================================");
            }
        }        
    }
}