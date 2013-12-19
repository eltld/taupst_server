package test.util.sync;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface Sysn {
	public Map<String, String> login() throws ClientProtocolException, IOException;
}
