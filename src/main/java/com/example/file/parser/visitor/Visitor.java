package com.example.file.parser.visitor;

import com.example.file.parser.dto.Order;

public interface Visitor {

    void performValidation(Order order);
}
