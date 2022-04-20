package com.packet.indoor.config.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.packet.indoor.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBody {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private String path;
    private Integer status;
    private String error;
    private String message;

    public static ErrorBody create(HttpServletRequest request, HttpStatus httpStatus, Exception e){
        return ErrorBody.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
