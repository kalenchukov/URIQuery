/*
 * Copyright © 2021 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 */

package dev.kalenchukov.uriquery;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UriQuery
{
	private UriQuery() {}

	/**
	 * Разбирает параметры URI
	 *
	 * @param uriQueryEncode закодированные параметры URI
	 * @return карту параметров и их значений
	 * @throws IllegalArgumentException если переданы некорректный формат параметров URI
	 */
	public static Map<String, List<String>> parse(String uriQueryEncode) throws IllegalArgumentException
	{
		Map<String, List<String>> params = new LinkedHashMap<>();

		if (uriQueryEncode != null && uriQueryEncode.length() > 0)
		{
			for (String groupParam : uriQueryEncode.split("&"))
			{
				Pattern pattern = Pattern.compile("(?<key>.+(\\[\\])?)=(?<value>.*)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(groupParam);

				if (!matcher.matches())
				{
					throw new IllegalArgumentException("Not correct query");
				}

				String keyGroup = URLDecoder.decode(matcher.group("key"), StandardCharsets.UTF_8);
				String valueGroup = URLDecoder.decode(matcher.group("value"), StandardCharsets.UTF_8);

				List<String> paramValues = new ArrayList<>();

				if (keyGroup.contains("[]"))
				{
					keyGroup = keyGroup.replace("[]", "");

					if (params.containsKey(keyGroup))
					{
						paramValues = params.get(keyGroup);
					}
				}

				paramValues.add(valueGroup);
				params.put(keyGroup, paramValues);
			}
		}

		return params;
	}

	/**
	 * Собирает параметры URI
	 *
	 * @param params карта параметров и их значений
	 * @return закодированные параметры URI
	 */
	public static String compose(Map<String, List<String>> params)
	{
		StringBuilder uriQuery = new StringBuilder();

		boolean needSeparator = false;

		for (Map.Entry<String, List<String>> groupParam : params.entrySet())
		{
			if (needSeparator)
			{
				uriQuery.append("&");
			}

				for (int elm = 0; elm < groupParam.getValue().size(); elm++)
				{
					if (elm > 0)
					{
						uriQuery.append("&");
					}

					uriQuery.append(URLEncoder.encode(groupParam.getKey(), StandardCharsets.UTF_8));

					if (groupParam.getValue().size() > 1)
					{
						uriQuery.append("[]");
					}

					uriQuery.append("=");

					uriQuery.append(URLEncoder.encode(groupParam.getValue().get(elm), StandardCharsets.UTF_8));
				}

			needSeparator = true;
		}

		return uriQuery.toString();
	}
}
