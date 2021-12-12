package com.packet.indoor.repository.packet;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.packet.indoor.config.InfluxConfig;
import com.packet.indoor.domain.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PacketInfluxRepository implements PacketRepository{

    @Autowired
    private InfluxConfig influxConfig;

    private InfluxDBClient influxClient(){
        return InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
    }

    @Override
    public void save(Packet packet) {
        InfluxDBClient client = influxClient();
        WriteApiBlocking writeApi = client.getWriteApiBlocking();

        writeApi.writeMeasurement(WritePrecision.S, packet);

        client.close();
    }

    @Override
    public List<Packet> findPackets(String tagId, LocalDateTime from, LocalDateTime to) {
        InfluxDBClient client = influxClient();
        QueryApi queryApi = client.getQueryApi();

        return null;
    }

    @Override
    public List<Packet> findAllPackets(String tagId) {
        InfluxDBClient client = influxClient();
        QueryApi queryApi = client.getQueryApi();

        String flux = "from(bucket:\"PacketManager\") " +
                "|> range(start: -30d)" +
                "|> filter(fn: (r) => " +
                  "r.tag == \"" + tagId + "\"" +
                ")";

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();
            for (FluxRecord record : records) {
                System.out.println(record.getValues());
            }
        }
        return null;
    }
}
