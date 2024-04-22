package ai.deeplay.animalsaccountancesystem;

import ai.deeplay.animalsaccountancesystem.common.expression.IExpression;
import ai.deeplay.animalsaccountancesystem.data.IDataParser;
import ai.deeplay.animalsaccountancesystem.data.io.JsonFileReader;
import ai.deeplay.animalsaccountancesystem.request.parser.RequestParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AnimalsAccountanceSystemApplication implements CommandLineRunner {

	private final JsonFileReader dataReader;
	private final IDataParser dataParser;
	private final RequestParser requestParser;

	public static void main(String[] args) {
		SpringApplication.run(AnimalsAccountanceSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Application running");
		Path dataPath = Path.of(args[0]);
		String request = args[1];
		dataReader.setFilePath(dataPath);
		var data = dataParser.dataFromString(dataReader.read());
		log.info("Datafile parsed");
		requestParser.setData(data);
		IExpression requestExpression = requestParser.proceedRequest(request);
		log.info("Request parsed");
		System.out.println("Number of animals fits criteria: " + requestExpression.evaluate().size());
	}

}
