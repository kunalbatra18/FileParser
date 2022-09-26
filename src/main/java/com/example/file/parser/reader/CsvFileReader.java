package com.example.file.parser.reader;

import com.example.file.parser.constants.Constants;
import com.example.file.parser.dto.Order;
import com.example.file.parser.visitor.ValidationVisitor;
import com.example.file.parser.visitor.Visitor;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Qualifier("csvFileReader")
public class CsvFileReader implements FileReader, Visitor {

    private Visitor validationVisitor;

    public CsvFileReader(ValidationVisitor validationVisitor) {
        this.validationVisitor = validationVisitor;
    }

    @Override
    public void process(String path) {
        readFileData(path);
    }

    private void readFileData(String fileName) {
        CSVReader csvReader = getReader(fileName);
        List<String[]> dataLineItems = getLines(csvReader);
        String[] headers  = dataLineItems.stream().findFirst().get();

        Map<String,Integer> headerToIndex = IntStream.range(0, headers.length)
                .boxed()
                .collect(Collectors.toMap(i->headers[i],i->i));

        dataLineItems.stream().skip(1).forEach(strings -> {
          Order order =  new Order();
          order.setOrderId(strings[headerToIndex.get(Constants.ORDER_ID)]);
          order.setAmount(strings[headerToIndex.get(Constants.AMOUNT)]);
          order.setCurrency(strings[headerToIndex.get(Constants.CURRENCY)]);
          order.setComment(strings[headerToIndex.get(Constants.COMMENT)]);
          performValidation(order);
        });


    }

    private List<String[]> getLines(CSVReader csvReader) {
        try {
            return  csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new IllegalArgumentException("error while reading file ",e);
        }
    }

    private CSVReader getReader(String path) {
        CSVReader csvReader = null;
        try {
            csvReader = getCsvReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvReader;
    }

    private CSVReader getCsvReader(String path) throws IOException {
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().withSeparator(',').build();
        return new CSVReaderBuilder(
                Files.newBufferedReader(Paths.get(path))).withCSVParser(rfc4180Parser).build();
    }

    @Override
    public void performValidation(Order order) {
        order.setId(order.getOrderId());
        validationVisitor.performValidation(order);
    }
}
