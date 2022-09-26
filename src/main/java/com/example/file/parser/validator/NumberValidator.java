package com.example.file.parser.validator;

public class NumberValidator extends Validator {

    @Override
    public Boolean validate(String value) {
        try{
            Double.valueOf(value);
        }catch (NumberFormatException e){
            return false;
        }
        return validateNext(value);
    }
}
