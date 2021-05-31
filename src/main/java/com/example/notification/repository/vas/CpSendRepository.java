package com.example.notification.repository.vas;

import com.example.notification.model.vas.CP_SEND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CpSendRepository extends JpaRepository<CP_SEND, Long> {

    @Query(
            value = "SELECT tg, (SUM( loi30 ) + SUM( loi26 ) + SUM( loi25 ) + SUM( loi24 ) + SUM( loi23 ) + " +
                    "SUM( loi19 ) + SUM( loi17 ) + SUM( loi13 ) + SUM( loi10 ) + SUM( loi8 ) + SUM( loi2 ) + " +
                    "SUM( loi1 ) + SUM( loi0 ) + SUM( loiother ) + SUM( loiuserpass )) AS tong_cdr," +
                    "SUM( loi30 ) AS loicdr10m, SUM( loi26 ) AS moikichhoat, SUM( loi25 ) AS quahanmuctin, " +
                    "SUM( loi24 ) AS quahmtininvite, SUM( loi23 ) AS tbkophaisub,SUM( loi19 ) AS khunggiokoguitin," +
                    "SUM( loi17 ) AS thuebaobichan, SUM( loi13 ) AS tbnodvairtime, SUM( loi10 ) AS gachlenhquahm," +
                    "SUM( loi8 ) AS tbkotontai, SUM( loi2 ) AS loidatabase, SUM( loi1 ) AS chuaxacdinh, SUM( loi0 ) AS thanhcong," +
                    "SUM( loiother ) AS loiother, SUM( loiuserpass ) AS loiuserpass " +
                    "FROM ( SELECT to_char( update_time, 'DD/MM/YYYY' ) AS tg, " +
                    "( CASE WHEN status = '-30' THEN COUNT( * ) ELSE 0 END ) AS loi30," +
                    "( CASE WHEN status = '-26' THEN COUNT( * ) ELSE 0 END ) AS loi26," +
                    "( CASE WHEN status = '-25' THEN COUNT( * ) ELSE 0 END ) AS loi25," +
                    "( CASE WHEN status = '-24' THEN COUNT( * ) ELSE 0 END ) AS loi24," +
                    "( CASE WHEN status = '-23' THEN COUNT( * ) ELSE 0 END ) AS loi23," +
                    "( CASE WHEN status = '-19' THEN COUNT( * ) ELSE 0 END ) AS loi19," +
                    "( CASE WHEN status = '-17' THEN COUNT( * ) ELSE 0 END ) AS loi17," +
                    "( CASE WHEN status = '-13' THEN COUNT( * ) ELSE 0 END ) AS loi13," +
                    "( CASE WHEN status = '-10' THEN COUNT( * ) ELSE 0 END ) AS loi10," +
                    "( CASE WHEN status = '-8' THEN COUNT( * ) ELSE 0 END ) AS loi8," +
                    "( CASE WHEN status = '2' THEN COUNT( * ) ELSE 0 END ) AS loi2," +
                    "( CASE WHEN status = '1' THEN COUNT( * ) ELSE 0 END ) AS loi1," +
                    "( CASE WHEN status = '0' THEN COUNT( * ) ELSE 0 END ) AS loi0," +
                    "( CASE WHEN status = '-31' THEN COUNT( * ) ELSE 0 END ) AS loiother," +
                    "( CASE WHEN status = '-5' THEN COUNT( * ) ELSE 0 END ) AS loiuserpass " +
                    "FROM cp_send WHERE  1 = 1  AND ( update_time ) BETWEEN TO_DATE( :startTime , 'DD/MM/YYYY HH24:MI:SS' ) " +
                    "AND TO_DATE( :endTime, 'DD/MM/YYYY HH24:MI:SS' ) " +
                    "GROUP BY to_char( update_time, 'DD/MM/YYYY' ),status) " +
                    "GROUP BY tg",
            nativeQuery = true
    )
    List<Object[]> getCPSend(@Param("startTime") String startTime, @Param("endTime") String endTime);


    @Query(
            value = "SELECT TG, ( sum( succes ) + sum( blacklist ) + sum( loi24h ) + sum( quaHM ) + sum( noVTT ) + sum( KoPhaiMT ) + sum( no45Day ) ) AS tong_cdr," +
                    "sum( succes ) AS thanhCong," +
                    "sum( blacklist ) AS blacklist," +
                    "sum( loi24h ) AS loi24h," +
                    "sum( quaHM ) AS quaHM," +
                    "sum( noVTT ) AS noVTT," +
                    "sum( KoPhaiMT ) AS KoPhaiMT," +
                    "sum( no45Day ) AS no45Day " +
                    "FROM (  SELECT to_char( UPDATE_TIME, 'DD/MM/YYYY' ) AS tg," +
                    "( CASE WHEN STATUS_SUB = '0000' THEN count( * ) ELSE 0 END ) AS succes," +
                    "( CASE WHEN STATUS_SUB = '0001' THEN count( * ) ELSE 0 END ) AS blacklist," +
                    "( CASE WHEN STATUS_SUB = '0002' THEN count( * ) ELSE 0 END ) AS loi24h," +
                    "( CASE WHEN STATUS_SUB = '0003' THEN count( * ) ELSE 0 END ) AS quaHM," +
                    "( CASE WHEN STATUS_SUB = '0004' THEN count( * ) ELSE 0 END ) AS noVTT," +
                    "( CASE WHEN STATUS_SUB = '0005' THEN count( * ) ELSE 0 END ) AS KoPhaiMT," +
                    "( CASE WHEN STATUS_SUB = '0006' THEN count( * ) ELSE 0 END ) AS no45Day " +
                    "FROM CDRTS " +
                    "WHERE  1 = 1 " +
                    "AND ( UPDATE_TIME ) BETWEEN TO_DATE( :startTime, 'DD/MM/YYYY HH24:MI:SS' ) " +
                    "AND TO_DATE( :endTime, 'DD/MM/YYYY HH24:MI:SS' ) " +
                    "GROUP BY " +
                    "to_char( UPDATE_TIME, 'DD/MM/YYYY' ), STATUS_SUB) " +
                    "GROUP BY TG ",
            nativeQuery = true
    )
    List<Object[]> getCDRTS(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
