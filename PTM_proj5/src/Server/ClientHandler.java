package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	void handleClient(InputStream clientInput, OutputStream clientOutput);
}
