package com.example.notification.service.impl;


import com.example.notification.repository.alpvoice.RequestInfoRepository;
import com.example.notification.service.VoiceToTextNotificationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoiceToTextNotificationServiceImpl implements VoiceToTextNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceToTextNotificationServiceImpl.class);
    @Autowired
    RequestInfoRepository requestInfoRepository;
    private String API_URL = "https://api.telegram.org/bot";
    private String TOKEN = "1709895263:AAHKyQ4fk0RC3Hfps1v8UpsBE88O2c8M6-o";
    @Autowired
    private UsersServiceImpl usersService;

    public static double round(double num, int digits) {

        // epsilon correction
        double n = Double.longBitsToDouble(Double.doubleToLongBits(num) + 1);
        double p = Math.pow(10, digits);
        return Math.round(n * p) / p;
    }

    @Scheduled(cron="0 0 * * * *", zone = "Asia/Ho_Chi_Minh")
    public void scheduleFixedDelayTask() throws InterruptedException {
        ZoneId swissZone = ZoneId.of("Asia/Ho_Chi_Minh");
        System.out.println("Task1 - " + new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now(swissZone).minus(60, ChronoUnit.MINUTES);
        String end = now.format(formatter);
        String start = now.minus(60, ChronoUnit.MINUTES).format(formatter);
        String endOtherFormat = now.format(formatter2);
        String startOtherFormat = now.minus(60, ChronoUnit.MINUTES).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        List<Object[]> objects = requestInfoRepository.getResultByTime(start, end);
        System.out.println("Object[]" + objects);
        String[] label = {"Converted", "Hangup", "Unconverted"};
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            Object[] o = null;
            try {
                o = objects.get(i);
            } catch (Exception ex) {
                o = null;
                ex.printStackTrace();
            }
            map.put(label[i], o != null ? o[1] : 0);
        }

        Long total = 0L;
        Long converted = Long.valueOf(map.get("Converted").toString());
        Long hangUp = Long.valueOf(map.get("Hangup").toString());
        Long unconverted = Long.valueOf(map.get("Unconverted").toString());

        total += converted;
        total += hangUp;
        total += unconverted;
        map.put("Total", total);

        if (total > 0) {
            double convertedPercent = round((float) converted * 100 / total, 2);
            double hangupPercent = round((float) hangUp * 100 / total, 2);
            double unconvertedPercent = round((float) 100 - convertedPercent - hangupPercent, 2);

            TelegramBot bot = new TelegramBot(TOKEN);
            SendMessage request = new SendMessage("-590682689", "<strong>\uD83D\uDC49 </strong> "
                    + "<strong>Voice to text: " + startOtherFormat + " </strong> -> <strong> " + endOtherFormat + "</strong>\n"
                    + "<strong>- Total: " + total + " </strong> \n"
                    + "<strong>- Converted: " + converted + " - " + convertedPercent + "%." + "</strong> \n"
                    + "<strong>- Hangup: " + hangUp + " - " + hangupPercent + "%." + "</strong> \n"
                    + "<strong>- Uncoverted: " + unconverted + " - " + unconvertedPercent + "%</strong>"
            )
                    .parseMode(ParseMode.HTML);

            SendResponse sendResponse = bot.execute(request);
            boolean ok = sendResponse.isOk();
            System.out.println("Is send : " + ok);
            Message message = sendResponse.message();
            System.out.println("Message: " + message);

        }
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Ho_Chi_Minh")
    public void scheduleFixedDelay() throws InterruptedException {
        ZoneId swissZone = ZoneId.of("Asia/Ho_Chi_Minh");
        System.out.println("Task1 - " + new Date());
        RestTemplate restTemplate = new RestTemplate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime now = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        String endString = end.format(formatter);
        String startString = start.format(formatter);
        String today = start.format(formatter2);
        System.out.println(start + " " + end);
        Map<String, Long> stringLongMap = usersService.getAllUser(startString, endString);
        TelegramBot bot = new TelegramBot(TOKEN);
        SendMessage request = new SendMessage("-590682689", "<strong>\uD83D\uDC49 " +
                "Báo cáo ngày " + today + "</strong>\n" +
                "<strong>- Tổng CDR trong ngày: " + stringLongMap.get("totalCDR") + " </strong> \n" +
                "<strong>- Tổng tin gửi tới SMSC: " + stringLongMap.get("SMSC") + " </strong> \n" +
                "<strong>- Tổng tin gửi thành công tới TB: " + stringLongMap.get("Success") + "</strong> \n" +
                "<strong>- Tỉ lệ gửi thành công: " + stringLongMap.get("successPercent") + "%</strong>")
                .parseMode(ParseMode.HTML);

        SendResponse sendResponse = bot.execute(request);
        boolean ok = sendResponse.isOk();
        System.out.println("Is send : " + ok);
        Message message = sendResponse.message();
        System.out.println("Message: " + message);


    }


}
