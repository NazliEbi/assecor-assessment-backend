package com.springboot.project.api.util;

import java.util.HashMap;
import java.util.Map;

public enum Color {
	blau(1),
	gruen(2),
	violett(3),
	rot(4),
	gelb(5),
	tuerkis(6),
	weiß(7);
	
	private final int Value;

	Color(int value) {
		this.Value = value;
	}
	
	private static final Map<Integer, Color> mapOfColours = new HashMap<Integer, Color>();
	
	static
    {
        for (Color color : Color.values())
        	mapOfColours.put(color.Value, color);
    }
	
	public static Color from(int value) {
		return mapOfColours.get(value);	
	}

	public static Integer numberOfColor(String value) {
		int key = 0;
		for(Map.Entry<Integer, Color> entry : mapOfColours.entrySet()) {
		    String color =	entry.getValue().toString();
			if(color.equals(value)) {
				key =  entry.getKey();
				break;
			}
		}
		return key;
	}
}
