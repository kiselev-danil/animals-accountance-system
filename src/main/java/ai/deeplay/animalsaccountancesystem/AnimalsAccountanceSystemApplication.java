package ai.deeplay.animalsaccountancesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimalsAccountanceSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AnimalsAccountanceSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello world!");
	}
}
