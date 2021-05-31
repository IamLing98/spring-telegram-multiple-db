package com.example.notification.service.impl;

import com.example.notification.repository.vas.CpSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl {

    @Autowired
    private CpSendRepository cpSendRepository;

    public Map<String, Long> getAllUser(String start, String end) {
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        Map<String, Long> stringLongMap = new HashMap<>();
        List<Object[]> objectsList = cpSendRepository.getCPSend(start, end);
        if (objectsList != null && objectsList.size() > 0) {
            Object[] objects = objectsList.get(0);
            System.out.println("Object: " + objects[objects.length - 3]);
        }
        List<Object[]> objectList2 = cpSendRepository.getCDRTS(start, end);
        if (objectList2 != null && objectsList != null) {
            Object[] objects = objectsList.get(0);
            stringLongMap.put("totalCDR", Long.parseLong(objectList2.get(0)[1].toString()));
            stringLongMap.put("SMSC", Long.parseLong(objectList2.get(0)[2].toString()));
            stringLongMap.put("Success", Long.parseLong(objects[objects.length - 3].toString()));
            stringLongMap.put("successPercent",
                    Long.parseLong(objects[objects.length - 3].toString()) * 100 / Long.parseLong(objectList2.get(0)[2].toString()));
        }

        return stringLongMap;
    }

}
