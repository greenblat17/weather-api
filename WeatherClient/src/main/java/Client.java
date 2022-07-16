import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Client {

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите название \"сенсора\"");
//        String sensorName = scanner.nextLine();
//
//        registrationSensor(sensorName);
//
//        double minTemperature = -20;
//        double maxTemperature = 30;
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            addMeasurements(
//                    random.nextDouble() * (i < 25 ? minTemperature : maxTemperature),
//                    (i >= 25 && random.nextBoolean()),
//                    sensorName
//            );
//        }

        getTemperature();
    }

    public static void registrationSensor(String sensorName) {

        String url = "http://localhost:8080/api/v1/sensor/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequest(url, jsonData);


    }

    public static void addMeasurements(double value, boolean isRaining, String sensorName) {

        String url = "http://localhost:8080/api/v1/measurement/add";


        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", isRaining);
        jsonData.put("sensor", Collections.singletonMap("name", sensorName));


        makePostRequest(url, jsonData);

    }

    public static void makePostRequest(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }

    }

    public static void getTemperature() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/measurement";

        List response = restTemplate.getForObject(url, List.class);

        if (response == null) {
            return;
        }

        for (int i = 0; i < response.size(); i++) {
            System.out.println(response.get(i));
        }
    }

}
