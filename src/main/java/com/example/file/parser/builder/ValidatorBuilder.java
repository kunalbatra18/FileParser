package com.example.file.parser.builder;

import com.example.file.parser.validator.NullValidator;
import com.example.file.parser.validator.NumberValidator;
import com.example.file.parser.validator.Validator;

public class ValidatorBuilder {


    public static Validator getNullValidator() {
        return new NullValidator();
    }

    public static Validator getNumberValidator(){
       return Validator.create(new NullValidator(),new NumberValidator());
    }
}
