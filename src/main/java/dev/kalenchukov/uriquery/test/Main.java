/*
 * Copyright © 2022 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 */

package dev.kalenchukov.uriquery.test;

import dev.kalenchukov.uriquery.UriQuery;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main
{
	public static void main(String[] args)
	{
		String uriQueryLine =
			"param1=value1.1" +
			"&param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1" +
			"&param3[]=value3.1" +
			"&param3[]=value3.2" +
			"&param4=value4.1";

		// Вызов разборщика параметров
		System.out.println(UriQuery.parse(uriQueryLine));
		/*
			Результат выполнения:
			{
				param1=[value1.1],
				param2=[значение2.1],
				param3=[value3.1, value3.2],
				param4=[value4.1]
			}
		*/

		Map<String, List<String>> uriQueryMap = new LinkedHashMap<>(
			Map.ofEntries(
				Map.entry("param1", List.of("value1.1")),
				Map.entry("param2", List.of("значение2.1")),
				Map.entry("param3", List.of("value3.1", "value3.2")),
				Map.entry("param4", List.of("value4.1"))
			)
		);

		// Вызов сборщика параметров
		System.out.println(UriQuery.compose(uriQueryMap));
		/*
			Результат выполнения:
			param4=value4.1&param3[]=value3.1&param3[]=value3.2&param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1&param1=value1.1
		*/
	}
}
