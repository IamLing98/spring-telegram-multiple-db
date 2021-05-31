package com.example.notification.dto;

import com.example.notification.config.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfoDTO {

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    LocalDateTime createdTime;

    private String requestId;

    private String audioId;

    private Long totalTimeRequest;

    private Long startTimeRequest;

    private String fileName;

    private String filePath;

    private Integer vaisStatus;

    private String xServiceType;

    private String xReference;

    private String xReturnUrl;

    private String scrid;

    private String viettelStatus = "Unconverted";

    private String conversionText;

    private String xLanguage;

    private String contentType;

    private String encoding;

    private Long uploadTiming;

    private String sendToViettelStatus;

    private Boolean isSendToViettel = false;

    private Float fileDuration;

}
