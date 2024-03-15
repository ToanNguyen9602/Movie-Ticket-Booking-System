package com.demo.helpers;

import java.util.UUID;

public class CodeHelper {
	public static String generate()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
}
