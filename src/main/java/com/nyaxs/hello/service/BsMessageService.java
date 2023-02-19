package com.nyaxs.hello.service;


import com.nyaxs.hello.domain.BsMessage;
import com.nyaxs.hello.mapper.BsMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BsMessageService {

    @Autowired
    private BsMessageMapper messageMapper;
    public int insert(BsMessage message){
        int insert = messageMapper.insert(message);
        return insert;
    }

}
