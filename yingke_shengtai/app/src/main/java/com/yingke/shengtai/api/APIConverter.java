package com.yingke.shengtai.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class APIConverter implements JsonDeserializer<Object>, JsonSerializer<Object> {

	@Override
	public JsonElement serialize(Object object, Type type, JsonSerializationContext context) {
		return null;
	}

	@Override
	public Object deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		return null;
	}

}
