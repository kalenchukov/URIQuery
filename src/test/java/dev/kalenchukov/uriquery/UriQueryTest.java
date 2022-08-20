/*
 * Copyright © 2022 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kalenchukov.uriquery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UriQueryTest
{
	private static URI QUERY_URI;

	private static Map<String, String[]> QUERY_MAP;

	@Before
	public void setUp()
	{
		QUERY_MAP = new LinkedHashMap<>();
		QUERY_MAP.put("param1", new String[]{"value1.1"});
		QUERY_MAP.put("param2", new String[]{"значение2.1"});
		QUERY_MAP.put("param3", new String[]{"value3.1", "value3.2"});
		QUERY_MAP.put("param4", new String[]{"value4.1"});
		QUERY_MAP.put("param5", new String[]{null});

		StringBuilder query = new StringBuilder();
		query.append("?");
		query.append("param1=value1.1");
		query.append("&");
		query.append("param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1");
		query.append("&");
		query.append("param3[]=value3.1");
		query.append("&");
		query.append("param3[]=value3.2");
		query.append("&");
		query.append("param4=value4.1");
		query.append("&");
		query.append("param5=");

		QUERY_URI = URI.create(query.toString());
	}

	@After
	public void tearDown()
	{
		QUERY_URI = URI.create("");
		QUERY_MAP.clear();
	}

	/**
	 * Проверка количества параметров
	 */
	@Test
	public void testParseParamCount()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_URI);

		assertEquals(
			QUERY_MAP.size(),
			queryMap.size()
		);
	}

	/**
	 * Проверка идентичности параметров
	 */
	@Test
	public void testParseParamEquals()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_URI);

		for (Map.Entry<String, String[]> entryQueryMap : queryMap.entrySet())
		{
			assertTrue(QUERY_MAP.containsKey(entryQueryMap.getKey()));
		}
	}

	/**
	 * Проверка количества значений у параметров
	 */
	@Test
	public void testParseParamValueCount()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_URI);

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
	public void testParseParamValueEquals()
	{
		Map<String, String[]> queryMap = UriQuery.parse(QUERY_URI);

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

		assertEquals(QUERY_URI.getRawQuery(), queryString);
	}
}