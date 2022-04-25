package com.packet.indoor.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.packet.indoor.config.InfluxConfig;
import com.packet.indoor.domain.Location;
import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class InfluxService {

    @Autowired
    private InfluxConfig influxConfig;

    private InfluxDBClient influxClient(){
        return InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
    }

    public List<Location> findLocationOfAssignedBoard(AssignedBoard assignedBoard) {
        InfluxDBClient client = influxClient();
        QueryApi queryApi = client.getQueryApi();
        String start = assignedBoard.getAssignedAt().toInstant(ZoneOffset.UTC).toString();
        String stop = (!assignedBoard.getAssigned()) ? assignedBoard.getUnAssignedAt().toInstant(ZoneOffset.UTC).toString() : LocalDateTime.now().toInstant(ZoneOffset.UTC).toString();

        String flux = "from(bucket:\"" +  influxConfig.getBucket() + "\")\n" +
                "|> range(start: -30d)\n" +
//                "|> range(start: " + start + ", stop: " + stop + ")\n" +
                "|> filter(fn: (r) => r[\"_measurement\"] == \"mqtt_schema\")\n" +
                "|> filter(fn: (r) => r[\"tag\"] == \"" + assignedBoard.getInfluxTag() + "\")\n" +
                "|> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";

        List<FluxTable> tables = queryApi.query(flux);
        List<Location> locations = new ArrayList<>();

        for (FluxTable table : tables){
            for (FluxRecord record : table.getRecords()) {
                Location location = Location.create(record, assignedBoard.getVisitor());
                locations.add(location);
            }
        }

        return locations;
    }

    public void example() {
        String token = "<INFLUX_TOKEN>";
        String org = "Database_Eugene";

        try (InfluxDBClient client = InfluxDBClientFactory.create("https://us-east-1-1.aws.cloud2.influxdata.com", token.toCharArray())) {

            String query = "option v = {timeRangeStart: 2022-04-12T17:00:00Z, timeRangeStop: 2022-04-12T19:00:00Z}\n" +
                    "\n" +
                    "from(bucket: \"MQTT\")\n" +
                    " |> range(start: v.timeRangeStart, stop: v.timeRangeStop)\n" +
                    " |> range(start: v.timeRangeStart, stop: v.timeRangeStop)\n" +
                    " |> filter(fn: (r) => r[\"_measurement\"] == \"mqtt_schema\")\n" +
                    " |> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";

            List<FluxTable> tables = client.getQueryApi().query(query, org);

            for (FluxTable table : tables) {
                for (FluxRecord record : table.getRecords()) {
                    System.out.println(record);
                }
            }
        }
    }
}
