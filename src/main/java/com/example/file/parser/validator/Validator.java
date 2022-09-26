package com.example.file.parser.validator;

public abstract class Validator {

    private Validator next;

    public static Validator create(Validator first,Validator... validators){
        Validator head = first;
        Validator temp = head;
        for (int i = 0;i< validators.length;i++){
            temp.next = validators[i];
        }
        return head;
    }

    public abstract Boolean validate(String value);

    public boolean validateNext(String value){
        if(next==null){
            return true;
        }
        return validate(value);
    }


}