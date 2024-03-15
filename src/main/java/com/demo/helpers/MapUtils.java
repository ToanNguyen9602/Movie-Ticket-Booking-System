package com.demo.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.lang.Nullable;

public class MapUtils {
	public static <T extends Object> Map<String, T> sortByKeyString(Map<String, T> map) {
		// Create a new LinkedHashMap to store the sorted entries
		Map<String, T> sortedMap = new LinkedHashMap<>();

		// Sort the entries based on keys
		map.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

		return sortedMap;
	}

	public static <K extends Object, V extends Object> void println(Map<K, V> map) {
		// Iterate over the entries and print each key-value pair
		for (Map.Entry<K, V> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public static <T extends Object> Map<String, T> captializeKey(Map<String, T> map) {
		Map<String, T> capitalizedMap = new HashMap<>();

		// Capitalize the keys and copy values
		for (Map.Entry<String, T> entry : map.entrySet()) {
			String capitalizedKey = entry.getKey().toUpperCase(); // Capitalize the key
			capitalizedMap.put(capitalizedKey, entry.getValue()); // Copy the value
		}

		return capitalizedMap;
	}

	public static <K extends Object, V extends Object> List<K> getKeyList(Map<K, V> map) {
		return new ArrayList<K>(map.keySet());
	}

	public static <K, V> List<V> getListValue(Map<K, V> map, @Nullable List<K> keys) {
		if (keys == null) return new ArrayList<>(map.values());
		List<V> values = new ArrayList<>();
		for (K key : keys) {
			if (map.containsKey(key)) {
				values.add(map.get(key));
			}
		}
		return values;
	}
}
