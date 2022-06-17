package com.macademia.era.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * created by ysnky on Jun 14, 2022
 *
 */
public class GlobalUtil {

	private static Logger logger = LoggerFactory.getLogger(GlobalUtil.class);	

	
	public static String exceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	
	public static <T> T toModel(String json, Class<T> _class) {
        try {
			return new ObjectMapper().readValue(json, _class);
		} catch (Exception e) {
			logger.error(exceptionToString(e));
		}	
        return null;
	}
	
	
	public static <T> T toModel(Object obj, Class<T> _class) {
        String json = GlobalUtil.toJson(obj);
        return toModel(json, _class);
	}
	

	public static String toJson(Object model) {

		String jsonStr = null;

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			jsonStr = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
		}

		return jsonStr;
	}

}
