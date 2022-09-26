package com.example.file.parser.validator;

import org.springframework.util.ObjectUtils;

public class NullValidator extends Validator {

    @Override
    public Boolean validate(String value) {
        if(ObjectUtils.isEmpty(value)){
            return false;
        }
        return validateNext(value);
    }
}
