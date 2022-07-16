import dto.MeasurementDTO;
import dto.MeasurementResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;

public class DrawGraphicTemperature {

    public static void main(String[] args) {
        double[] yData = getTemperature();
        double[] xData = IntStream.range(0, yData.length).asDoubleStream().toArray();

        XYChart chart = QuickChart.getChart("Sample Chart", "Measurement", "Temperature", "temperature", xData, yData);

        new SwingWrapper(chart).displayChart();
    }

    public static double[] getTemperature() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/measurements";

        MeasurementResponse response = restTemplate.getForObject(url, MeasurementResponse.class);

        if (response == null || response.getMeasurements() == null) {
            return new double[0];
        }

        List<MeasurementDTO> measurements = response.getMeasurements();

        double[] temperature = new double[measurements.size()];
        for (int i = 0; i < measurements.size(); i++) {
            temperature[i] = measurements.get(i).getValue();
        }
        return temperature;
    }
}
