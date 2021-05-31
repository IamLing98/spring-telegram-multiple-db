package com.example.notification.repository.alpvoice;

import com.example.notification.model.alpvoice.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, String> {
    @Query(
            value = "SELECT VIETTEL_STATUS , count(VIETTEL_STATUS) from REQUEST_INFO WHERE CREATED_TIME BETWEEN :startTime AND :endTime GROUP BY (VIETTEL_STATUS) ",
            nativeQuery = true
    )
    List<Object[]> getResultByTime(@Param("startTime") String startTime,@Param("endTime")  String endTime);
}
