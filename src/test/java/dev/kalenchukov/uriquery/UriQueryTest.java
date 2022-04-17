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
import java.util.Map;

import static org.junit.Assert.*;

public class UriQueryTest
{
	private static final StringBuilder QUERY_STRING = new StringBuilder();

	private static final Map<String, String[]> QUERY_MAP = new LinkedHashMap<>();

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
		QUERY_STRING.append("&");
		QUERY_STRING.append("param5=");

		QUERY_MAP.put("param1", new String[]{"value1.1"});
		QUERY_MAP.put("param2", new String[]{"значение2.1"});
		QUERY_MAP.put("param3", new String[]{"value3.1", "value3.2"});
		QUERY_MAP.put("param4", new String[]{"value4.1"});
		QUERY_MAP.put("param5", new String[]{null});
	}

	@After
	public void tearDown()
	{
		QUERY_STRING.setLength(0);
		QUERY_MAP.clear();
	}

	/**
	 * Проверка количества параметров
	 */
	@Test
	public void testParse1()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_STRING.toString());

		assertEquals(
			QUERY_MAP.size(),
			queryMap.size()
		);
	}

	/**
	 * Проверка идентичности параметров
	 */
	@Test
	public void testParse2()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_STRING.toString());

		for (Map.Entry<String, String[]> entryQueryMap : queryMap.entrySet())
		{
			assertTrue(QUERY_MAP.containsKey(entryQueryMap.getKey()));
		}
	}

	/**
	 * Проверка количества значений у параметров
	 */
	@Test
	public void testParse3()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_STRING.toString());

		for (Map.Entry<String, String[]> entryQueryMap : queryMap.entrySet())
		{
			assertEquals(
				QUERY_MAP.get(entryQueryMap.getKey()).length,
				queryMap.get(entryQueryMap.getKey()).length
			);
		}
	}

	/**
	 * Проверка идентичности значений у параметров
	 */
	@Test
	public void testParse4()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_STRING.toString());

		for (Map.Entry<String, String[]> entryQueryMap : queryMap.entrySet())
		{
			for (int elm = 0; elm < QUERY_MAP.get(entryQueryMap.getKey()).length; elm++)
			{
				if (QUERY_MAP.get(entryQueryMap.getKey())[elm] == null)
				{
					assertEquals(
						"",
						queryMap.get(entryQueryMap.getKey())[elm]
					);
				}
				else
				{
					assertEquals(
						QUERY_MAP.get(entryQueryMap.getKey())[elm],
						queryMap.get(entryQueryMap.getKey())[elm]
					);
				}
			}
		}
	}

	/**
	 * Проверка идентичности строки запроса
	 */
	@Test
	public void testCompose()
	{
		String queryString = UriQuery.compose(QUERY_MAP);

		assertEquals(QUERY_STRING.toString(), queryString);
	}
}