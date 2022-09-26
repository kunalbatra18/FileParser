package com.example.file.parser.validator;

import com.example.file.parser.builder.ValidatorBuilder;
import com.example.file.parser.constants.Constants;
import com.example.file.parser.dto.Order;
import com.example.file.parser.reader.CsvFileReader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {

    private final BlockingQueue<Order> obj;
    private AtomicInteger lineNumber = new AtomicInteger(0);


    public Consumer(BlockingQueue<Order> obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Order order = obj.take();
                lineNumber.getAndIncrement();
                order.setLineNumber(lineNumber.get());
                Validator numberValidator = ValidatorBuilder.getNumberValidator();
                Validator nullValidator = ValidatorBuilder.getNullValidator();
                StringBuilder errorMessage = new StringBuilder();
                fireValidationRules(order, numberValidator, nullValidator, errorMessage);
                if(errorMessage.length()!=0){
                    order.setResult(errorMessage.toString());
                }else {
                    order.setResult("OK");
                }

                System.out.println("order"+order);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fireValidationRules(Order order, Validator numberValidator, Validator nullValidator, StringBuilder errorMessage) {
        validateAttribute(order.getAmount(), Constants.AMOUNT, numberValidator, errorMessage);
        validateAttribute(order.getOrderId(),Constants.ORDER_ID, nullValidator, errorMessage);
        validateAttribute(order.getComment(),Constants.COMMENT, nullValidator, errorMessage);
        validateAttribute(order.getCurrency(),Constants.CURRENCY, nullValidator, errorMessage);
    }

    private void validateAttribute(String value,String attributeName, Validator validator,
                                   StringBuilder errorMessage) {
        if (isInvalidAttribute(value, validator)) {
            buildErrorMessage(value, errorMessage,attributeName);
        }
    }

    private void buildErrorMessage(String value, StringBuilder errorMessage,String attributeName) {
        errorMessage.append("invalid value "+value+" for "+ attributeName+" ");
    }


    private Boolean isInvalidAttribute(String value, Validator validator) {
        return ! validator.validate(value);
    }

}
