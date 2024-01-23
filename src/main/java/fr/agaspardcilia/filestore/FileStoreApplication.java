package fr.agaspardcilia.filestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class FileStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStoreApplication.class, args);
	}

}
