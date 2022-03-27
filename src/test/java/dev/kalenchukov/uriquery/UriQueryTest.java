/*
 * Copyright © 2022 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 */

package dev.kalenchukov.uriquery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UriQueryTest
{
	private static final StringBuilder QUERY_STRING = new StringBuilder();

	private static final Map<String, List<String>> QUERY_MAP = new LinkedHashMap<>();

	@Before
	public void setUp()
	{
		QUERY_STRING.append("param1=value1.1");
		QUERY_STRING.append("&");
		QUERY_STRING.append("param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1");
		QUERY_STRING.append("&");
		QUERY_STRING.append("param3[]=value3.1");
		QUERY_STRING.append("&");
		QUERY_STRING.append("param3[]=value3.2");
		QUERY_STRING.append("&");
		QUERY_STRING.append("param4=value4.1");

		QUERY_MAP.put("param1", List.of("value1.1"));
		QUERY_MAP.put("param2", List.of("значение2.1"));
		QUERY_MAP.put("param3", List.of("value3.1", "value3.2"));
		QUERY_MAP.put("param4", List.of("value4.1"));
	}

	@After
	public void tearDown()
	{
		QUERY_STRING.setLength(0);
		QUERY_MAP.clear();
	}

	@Test
	public void testParse()
	{
		Map<String, List<String>> queryMap = UriQuery.parse(QUERY_STRING.toString());

		assertEquals(
			QUERY_MAP.size(),
			queryMap.size()
		);

		for (Map.Entry<String, List<String>> entryQueryMap : queryMap.entrySet())
		{
			assertTrue(QUERY_MAP.containsKey(entryQueryMap.getKey()));

			assertEquals(
				QUERY_MAP.get(entryQueryMap.getKey()).size(),
				queryMap.get(entryQueryMap.getKey()).size()
			);

			assertTrue(QUERY_MAP.get(entryQueryMap.getKey()).containsAll(queryMap.get(entryQueryMap.getKey())));
		}
	}

	@Test
	public void testCompose()
	{
		String queryString = UriQuery.compose(QUERY_MAP);

		assertEquals(QUERY_STRING.toString(), queryString);
	}
}