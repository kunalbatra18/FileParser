package com.example.file.parser;

import com.example.file.parser.client.TestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class FileParserApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(FileParserApplication.class, args);
		TestClient testClient = app.getBean(TestClient.class);
		String arg = args[0];
			String[] filenames  = arg.split(",");
			for(String fileName: filenames)
			testClient.processFile("home\\"+fileName);
	}

}
