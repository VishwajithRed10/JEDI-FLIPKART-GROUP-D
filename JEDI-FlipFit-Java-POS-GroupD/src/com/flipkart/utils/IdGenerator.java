/**
 * 
 */
package com.flipkart.utils;

import java.util.HashSet;
/*
 *@Author : "Neelu Lalchandani"
 *@ClassName: "IdGenerator"
 *@Exceptions: "N/A"
 *@Version : "1.0"
 *@See : "java.util.HashSet"
 */
public class IdGenerator {

	static HashSet<String> alreadyAlloted = new HashSet<>();

	public static String generateId(String part) {
		String id = part + "_";

		while (true) {
			while (id.length() - part.length() < 4) {
				id += (int) Math.ceil((Math.random() + 1) * 10);
			}
			if(!alreadyAlloted.contains(id)) break;
			
		}
		return id;
	}

}
