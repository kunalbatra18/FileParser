package com.example.file.parser.reader;

import com.example.file.parser.dto.Order;
import com.example.file.parser.visitor.Visitor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Qualifier("jsonFileReader")
public class JsonFileReader implements FileReader, Visitor {

    private final Visitor validationVisitor;

    public JsonFileReader(Visitor validationVisitor) {
        this.validationVisitor = validationVisitor;
    }

    @Override
    public void process(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Order> myObjects = objectMapper.readValue(new File(fileName),
                    new TypeReference<List<Order>>(){});

            myObjects.forEach(order -> performValidation(order));
        } catch (IOException e) {
            throw new IllegalArgumentException("error while reading file ",e);
        }
    }

    @Override
    public void performValidation(Order order) {
        order.setId(order.getOrderId());
        validationVisitor.performValidation(order);
    }
}
