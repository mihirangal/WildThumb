package com.main.wildthumb;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matching {
	
	public static  ArrayList<String> CreateArrayList(String bigString)
	{
		ArrayList<String> arrayList  = new ArrayList<String>();
		String[] array = bigString.split("><");
		for (int i = 0; i < array.length; i++)
		{
			if (i == 0)
			{
				String str = array[i];
				str = str + '>';
				if(str.contains("<Name>"))
					arrayList.add(str.substring(6,str.length()-8));
			}
			else if (i == array.length - 1)
			{
				String str = array[i];
				str = '<' + str;
				if(str.contains("<Name>"))
					arrayList.add(str.substring(6,str.length()-8));
			}
			else
			{
				String str = array[i];
				str = '<' + str + '>';
				if(str.contains("<Name>"))
				arrayList.add(str.substring(6,str.length()-8));
			}
		}
		return arrayList;
	}
	
	public String DoMatching(String total)
	{
		String result = "";
		Pattern pattern = Pattern.compile("(?i)(<name.*?>)(.+?)(</name>)");
		Matcher matcher = pattern.matcher(total);
		if (matcher.find())
		{
		     result = matcher.group(2);
		}
		return result;
	}
}
