package com.example.file.parser;

import com.example.file.parser.client.TestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class FileParserApplication {

	public static void main(String[] args) throws IOException, URISyntaxException {
		ApplicationContext app = SpringApplication.run(FileParserApplication.class, args);
	TestClient testClient = app.getBean(TestClient.class);
	testClient.processFile("home\\test.csv");
		//String s = Paths.get(ClassLoader.getSystemResource("test.csv").toURI());

	/*	File file =  new File("test.txt");
		if(file.createNewFile()){
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getPath());
		}*/
	}

}
