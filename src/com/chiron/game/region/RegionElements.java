package com.chiron.game.region;

import java.util.ArrayList;
import java.util.List;

import com.chiron.util.GsonToCollectionParser;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class RegionElements extends GsonToCollectionParser<RegionElement> {

	private static final List<RegionElement> REGION_ELEMENTS = new ArrayList<>();
	
	public RegionElements() {
		super(REGION_ELEMENTS, "./data/json/region/region_elements.json");
	}
	
	public static ImmutableList<RegionElement> getRegionElements() {
		return ImmutableList.copyOf(REGION_ELEMENTS);
	}
	
	@Override protected RegionElement parseElement(JsonObject data, Gson builder) {
		int id = data.get("id").getAsInt();
		int[] keys = builder.fromJson(data.get("keys"), int[].class);
		return new RegionElement(id, keys);
	}

}
