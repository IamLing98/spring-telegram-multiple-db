package com.example.notification.model.alpvoice;

import com.example.notification.config.CustomLocalDateTimeSerializer;
import com.example.notification.dto.RequestInfoDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "REQUEST_INFO")
@Data
@Table(schema = "alpvoice", name = "REQUEST_INFO")
public class RequestInfo {

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Basic
    @Column(name = "CREATED_TIME")
    LocalDateTime createdTime;
    @Id
    @Column(name = "REQUEST_ID")
    private String requestId;
    @Column(name = "AUDIO_ID")
    private String audioId;
    @Column(name = "TOTAL_TIMING")
    private Long totalTimeRequest;
    @Basic
    @Column(name = "START_TIME_REQUEST")
    private Long startTimeRequest;
    @Basic
    @Column(name = "FILE_NAME")
    private String fileName;
    @Basic
    @Column(name = "FILE_PATH")
    private String filePath;
    @Basic
    @Column(name = "VAIS_STATUS")
    private Integer vaisStatus;
    @Basic
    @Column(name = "X_SERVICE_TYPE")
    private String xServiceType;
    @Basic
    @Column(name = "X_REFERENCE")
    private String xReference;
    @Basic
    @Column(name = "X_RETURN_URL")
    private String xReturnUrl;
    @Basic
    @Column(name = "SCRID")
    private String scrid;
    @Basic
    @Column(name = "VIETTEL_STATUS")
    private String viettelStatus = "Unconverted";
    @Basic
    @Column(name = "CONVERSION_TEXT")
    private String conversionText;
    @Basic
    @Column(name = "X_LANGUAGE")
    private String xLanguage;
    @Basic
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    @Basic
    @Column(name = "ENCODING")
    private String encoding;
    @Column(name = "UPLOAD_TIMING")
    private Long uploadTiming;

    @Column(name = "SEND_VIETTEL_STATUS")
    private String sendToViettelStatus;

    @Column(name = "IS_SEND_TO_VIETTEL")
    private Boolean isSendToViettel = false;

    @Basic
    @Column(name = "FILE_DURATION")
    private Float fileDuration;

    public RequestInfoDTO toDTO(ModelMapper modelMapper) {
        RequestInfoDTO requestInfoDTO = modelMapper.map(this, RequestInfoDTO.class);
        return requestInfoDTO;
    }
}
