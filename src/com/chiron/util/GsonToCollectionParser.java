package com.chiron.util;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class GsonToCollectionParser<T> {

	private final Gson builder = new GsonBuilder().create();
	
	private final Collection<T> collection;
	
	private final String path;
	
	public GsonToCollectionParser(Collection<T> collection, String path) {
		this.collection = collection;
		this.path = path;
	}

	public void parse() {
		try (FileReader reader = new FileReader(new File(path))) {
			JsonParser parser = new JsonParser();
			JsonArray array = (JsonArray) parser.parse(reader);
			for (int index = 0; index < array.size(); index++) {
				JsonObject data = (JsonObject) array.get(index);
				collection.add(parseElement(data, builder));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final Collection<T> getCollection() {
		return collection;
	}

	protected abstract T parseElement(JsonObject data, Gson builder);
}
