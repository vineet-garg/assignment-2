package crypto.webservice;

import crypto.webservice.resources.ServerResource;
import crypto.webservice.services.EncryptionSvc;
import crypto.webservice.services.EncryptionSvcImpl;
import crypto.webservice.services.StatsSvc;
import crypto.webservice.services.StatsSvcImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Server extends Application<ServerConfiguration> {

	@Override
	public void run(ServerConfiguration arg0, Environment arg1) throws Exception {
		EncryptionSvc encSvc = new EncryptionSvcImpl();
		StatsSvc statsSvc = new StatsSvcImpl();
		final ServerResource resource = new ServerResource(statsSvc, encSvc);
        arg1.jersey().register(resource);
	}

	public static void main(String[] args) throws Exception {
		new Server().run(args);

	}

}
