package crypto.webservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Server extends Application<ServerConfiguration> {

	@Override
	public void run(ServerConfiguration arg0, Environment arg1) throws Exception {
		final ServerResource resource = new ServerResource();
        arg1.jersey().register(resource);
	}

	public static void main(String[] args) throws Exception {
		new Server().run(args);

	}

}
