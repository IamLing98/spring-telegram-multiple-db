package com.example.notification.model.vas;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "SMSGW", name = "CP_SEND")
public class CP_SEND {

    @Id
    @Column(name = "ID")
//    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CP_SEND")
    @SequenceGenerator(sequenceName = "SEQ_CP_SEND", allocationSize = 1, name = "SEQ_CP_SEND")
    private Long id;

    @Basic
    @Column(name = "MSISDN")
    private String msisdn;

    @Basic
    @Column(name = "CONTENT")
    private String content;

    @Basic
    @Column(name = "STATUS")
    private Long status;

    @Basic
    @Column(name = "QUEUE_ID")
    private Long queueId;

    @Basic
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

}
