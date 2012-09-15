package wms.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class WMSResponseUtilities {
	private static Integer RESPONSE_BUFFER_BASE = 1024;
	private static Integer RESPONSE_BUFFER_FACTOR = 3;
	private static Integer RESPONSE_BUFFER = RESPONSE_BUFFER_BASE
			* RESPONSE_BUFFER_FACTOR;

	/**
	 * Method allowing to override values marked as static in
	 * {@link WMSResponseUtilities} class and that controlls data flow.
	 * 
	 * @param options
	 */
	public static void setProperties(Map<String, Object> options) {
		Field[] fields = WMSResponseUtilities.class.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())
					&& options.containsKey(f.getName())) {
				try {
					f.set(null, options.get(f.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void persistenceResponse(String data, HttpServletResponse res)
			throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream(RESPONSE_BUFFER);
		PrintWriter out = new PrintWriter(bytes, true);

		out.print(data);

		res.setContentLength(bytes.size());

		bytes.writeTo(res.getOutputStream());
	}

}
