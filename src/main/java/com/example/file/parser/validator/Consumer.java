package com.example.file.parser.validator;

import com.example.file.parser.builder.ValidatorBuilder;
import com.example.file.parser.dto.Order;
import com.example.file.parser.reader.CsvFileReader;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    BlockingQueue<Order> obj;


    public Consumer(BlockingQueue<Order> obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            while(true) {

                Order order = obj.take();
                Validator numberValidator = ValidatorBuilder.getNumberValidator();
                Validator nullValidator = ValidatorBuilder.getNullValidator();
                StringBuilder errorMessage = new StringBuilder();
                fireValidationRules(order, numberValidator, nullValidator, errorMessage);
                if(errorMessage.length()!=0){
                    order.setResult(errorMessage.toString());
                }

                System.out.println("order"+order);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fireValidationRules(Order order, Validator numberValidator, Validator nullValidator, StringBuilder errorMessage) {
        validateAttribute(order.getAmount(), CsvFileReader.AMOUNT, numberValidator, errorMessage);
        validateAttribute(order.getOrderId(),CsvFileReader.ORDER_ID, nullValidator, errorMessage);
        validateAttribute(order.getComment(),CsvFileReader.COMMENT, nullValidator, errorMessage);
        validateAttribute(order.getCurrency(),CsvFileReader.CURRENCY, nullValidator, errorMessage);
    }

    private void validateAttribute(String value,String attributeName, Validator validator,
                                   StringBuilder errorMessage) {
        if (isInvalidAttribute(value, validator)) {
            buildErrorMessage(value, errorMessage,attributeName);
        }
    }

    private void buildErrorMessage(String value, StringBuilder errorMessage,String attributeName) {
        errorMessage.append("invalid value "+value+" for "+ attributeName);
    }


    private Boolean isInvalidAttribute(String value, Validator validator) {
        return ! validator.validate(value);
    }

}
