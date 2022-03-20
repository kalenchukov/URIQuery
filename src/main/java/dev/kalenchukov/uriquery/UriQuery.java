/*
 * Copyright © 2022 Алексей Каленчуков
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UriQuery
{
	private UriQuery() {}

	/**
	 * Разбирает параметры URI
	 *
	 * @param uriQueryEncode закодированные параметры URI
	 * @return карту параметров и их значений
	 */
	@NotNull
	public static Map<String, List<String>> parse(@Nullable final String uriQueryEncode)
	{
		Map<String, List<String>> queryParams = new HashMap<>();

		if (uriQueryEncode == null || uriQueryEncode.length() < 3) {
			return queryParams;
		}

		for (String groupParam : uriQueryEncode.split("&"))
		{
			Pattern pattern = Pattern.compile("(?<param>[a-z0-9_\\-.+,|:]+(\\[\\])?)=(?<value>.*)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(groupParam);

			if (matcher.matches())
			{
				String paramGroup = matcher.group("param").toLowerCase();
				String valueGroup = URLDecoder.decode(matcher.group("value"), StandardCharsets.UTF_8);

				List<String> queryParamValues = new ArrayList<>();

				if (paramGroup.contains("[]"))
				{
					paramGroup = paramGroup.replace("[]", "");

					if (queryParams.containsKey(paramGroup)) {
						queryParamValues = queryParams.get(paramGroup);
					}
				}

				queryParamValues.add(valueGroup);
				queryParams.put(paramGroup, queryParamValues);
			}
		}

		return queryParams;
	}

	/**
	 * Собирает параметры URI
	 *
	 * @param params карта параметров и их значений
	 * @return закодированные параметры URI
	 */
	@NotNull
	public static String compose(@NotNull final Map<String, List<String>> params)
	{
		Objects.requireNonNull(params);

		StringBuilder uriQuery = new StringBuilder();

		boolean needSeparator = false;

		for (Map.Entry<String, List<String>> groupParam : params.entrySet())
		{
			if (needSeparator) {
				uriQuery.append("&");
			}

				for (int elm = 0; elm < groupParam.getValue().size(); elm++)
				{
					if (elm > 0) {
						uriQuery.append("&");
					}

					uriQuery.append(URLEncoder.encode(groupParam.getKey(), StandardCharsets.UTF_8));

					if (groupParam.getValue().size() > 1) {
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
