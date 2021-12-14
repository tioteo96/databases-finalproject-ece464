package com.packet.indoor.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.packet.indoor.domain.anchor.dto.AnchorRequestDto;

import java.io.IOException;

public class AnchorRequestDtoSerializer extends StdDeserializer<AnchorRequestDto> {

    public AnchorRequestDtoSerializer() {this(null);}

    protected AnchorRequestDtoSerializer(Class<AnchorRequestDto> t) {super(t);}

    @Override
    public AnchorRequestDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode productNode = p.getCodec().readTree(p);

        String manufacturer = productNode.get("manufacturer").asText();
        String description = productNode.get("description").asText();
        Double xCoordinate = productNode.get("xCoordinate").doubleValue();
        Double yCoordinate = productNode.get("yCoordinate").doubleValue();
        Double zCoordinate = productNode.get("zCoordinate").doubleValue();


        AnchorRequestDto requestDto = new AnchorRequestDto(manufacturer, description, xCoordinate, yCoordinate, zCoordinate);
        return requestDto;
    }
}
