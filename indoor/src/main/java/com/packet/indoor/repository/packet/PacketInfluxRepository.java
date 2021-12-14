package com.packet.indoor.repository.packet;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.packet.indoor.config.InfluxConfig;
import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.domain.packet.TagPackets;
import com.packet.indoor.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacketInfluxRepository implements PacketRepository{

    @Autowired
    private InfluxConfig influxConfig;
    @Autowired
    private TimeUtil timeUtil;

    private InfluxDBClient influxClient(){
        return InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
    }

    @Override
    public void save(Packet packet) {
        InfluxDBClient client = influxClient();
        WriteApiBlocking writeApi = client.getWriteApiBlocking();

        writeApi.writeMeasurement(WritePrecision.MS, packet);
        client.close();
    }

    @Override
    public void save(List<Packet> packets) {
        InfluxDBClient client = influxClient();
        WriteApiBlocking writeApi = client.getWriteApiBlocking();

        writeApi.writeMeasurements(WritePrecision.MS, packets);
        client.close();
    }

    @Override
    public List<Packet> findAllPackets(String tagId) {
        InfluxDBClient client = influxClient();
        QueryApi queryApi = client.getQueryApi();

        String flux = "from(bucket:\"" +  influxConfig.getBucket() + "\") " +
                "|> range(start: -30d)" +
                "|> filter(fn: (r) => " +
                  "r.tag == \"" + tagId + "\"" +
                ")";

        List<FluxTable> tables = queryApi.query(flux);
        List<Packet> packets = new ArrayList<>();
        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();
            for (FluxRecord record : records) {
                packets.add(Packet.create(record));
            }
        }
        client.close();
        return packets;
    }

    @Override
    public List<TagPackets> findTagPackets(String tagId, LocalDateTime from, LocalDateTime to, Integer quantity) {
        InfluxDBClient client = influxClient();
        QueryApi queryApi = client.getQueryApi();

        Instant fromInstant = timeUtil.toInstant(from);
        Instant toInstant = timeUtil.toInstant(to);

        String flux = "from(bucket:\"" + influxConfig.getBucket() + "\") " +
                "|> range(start: " + fromInstant.toString() + ", stop: " + toInstant.toString() +") " +
                "|> filter(fn: (r) => r[\"_measurement\"] == \"packet\" and r[\"tag\"] == \"" + tagId + "\") " +
                "|> aggregateWindow(every: 1m, fn: last, createEmpty: false) " +
                "|> keep(columns: [\"_time\", \"anchor\", \"_value\"]) " +
                "|> pivot(rowKey: [\"_time\"], columnKey: [\"anchor\"], valueColumn: \"_value\") " +
                "|> sort(columns: [\"_time\"], desc: true)";

        List<FluxTable> tables = queryApi.query(flux);
        List<TagPackets> tagPackets = new ArrayList<>();
        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();
            for (FluxRecord record : records) {
                tagPackets.add(TagPackets.create(record));
            }
        }
        client.close();
        return tagPackets;
    }


}
