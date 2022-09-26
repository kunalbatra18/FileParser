package com.example.file.parser.visitor;

import com.example.file.parser.dto.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@Qualifier("validationVisitor")
public class ValidationVisitor implements Visitor {

    private final BlockingQueue<Order> producer;

    public ValidationVisitor(BlockingQueue<Order> producer) {
        this.producer = producer;
    }

    @Override
    public void performValidation(Order order) {
        try {
            producer.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
