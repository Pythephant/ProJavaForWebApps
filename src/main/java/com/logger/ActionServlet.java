package com.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

@WebServlet(name = "actionServlet", urlPatterns = "/file")
public class ActionServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = (String) req.getAttribute("action");
		String content = null;
		if (action != null) {
			switch (action) {
			case "readFoo":
				content = readFile("foo.bar", false);
				break;
			case "readLicense":
				content = readFile("LICENSE", true);
				break;
			default:
				content = "Bad actions:" + action;
				log.warn("Action {} not allow", action);
				break;
			}
			if (content != null) {
				resp.getWriter().write(content);
			}

		} else {

		}

	}

	private String readFile(String fileName, boolean toDelete) {
		log.entry(fileName, toDelete);
		try {
			byte[] data = Files.readAllBytes(new File(fileName).toPath());
			log.info("Successful read file {}.", fileName);
			return log.exit(data.toString());
		} catch (Exception e) {
			log.error(MarkerManager.getMarker("COM_LOGGER"), "Failed to read file {}", fileName, e);
			return null;
		}
	}
}
