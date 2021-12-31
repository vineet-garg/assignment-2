package crypto.webservice;

import javax.xml.bind.DatatypeConverter;

import crypto.webservice.resources.ServerResource;
import crypto.webservice.services.EncryptionSvc;
import crypto.webservice.services.EncryptionSvcImpl;
import crypto.webservice.services.StatsSvc;
import crypto.webservice.services.StatsSvcImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Server extends Application<ServerConfiguration> {

	@Override
	public void run(ServerConfiguration config, Environment env) throws Exception {
		byte[] keyValue = DatatypeConverter.parseHexBinary(config.getKey());

		// Exception, if any, from initialization of Encryption Service is propagated
		
		EncryptionSvc encSvc = new EncryptionSvcImpl(keyValue, config.getKeyId());
		StatsSvc statsSvc = new StatsSvcImpl();
		final ServerResource resource = new ServerResource(statsSvc, encSvc, config.getKeyId());
		env.jersey().register(resource);
	}

	@Override
	public String getName() {
		return "crypto.webservice";
	}

	public static void main(String[] args) throws Exception {
		new Server().run(args);
	}
}
