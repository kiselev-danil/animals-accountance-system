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
		String request = removeSingleQuotas(args[1]);
		dataReader.setFilePath(dataPath);
		var data = dataParser.dataFromString(dataReader.read());
		requestParser.setData(data);
		IExpression requestExpression = requestParser.proceedRequest(request);
		System.out.println(requestExpression.evaluate().size());
	}

	public String removeSingleQuotas(String request) {
		StringBuilder sb = new StringBuilder(request);
		if((sb.charAt(0) == '\'') && (sb.charAt(sb.length()-1) == '\'')) {
			sb.delete(0,1);
			sb.delete(sb.length()-1,sb.length());
		}
		return sb.toString();
	}
}
