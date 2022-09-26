package com.example.file.parser.client;

import com.example.file.parser.reader.FileReader;
import com.example.file.parser.validator.Consumer;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class TestClient {

    @Autowired
    private FileReader csvFileReader;

    @Autowired
    private FileReader jsonFileReader;

    @Autowired
    private BlockingQueue producer;

    public void processFile(String path){
        if(FilenameUtils.getExtension(path).equals("csv")){
            processFile(csvFileReader,path);
        }
        if(FilenameUtils.getExtension(path).equals("json")){
            processFile(jsonFileReader,path);
        }
    }

    private void processFile(FileReader fileReader,String path){
        new Thread(() -> fileReader.process(path)).start();
        Consumer consumer =  new Consumer(producer);
        Thread t1 =  new Thread(consumer);
        t1.start();
    }
}
