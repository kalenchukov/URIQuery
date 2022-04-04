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

/**
 * Класс содержит статические методы для сборки / разборки параметров URI.
 */
public final class UriQuery
{
	/**
	 * Конструктор для {@code UriQuery} запрещающий создавать объект класса.
	 */
	private UriQuery() {}

	/**
	 * Разбирает параметры URI.
	 *
	 * @param query Закодированные параметры URI.
	 * @return Коллекцию параметров и их значений.
	 */
	@NotNull
	public static Map<@NotNull String, @NotNull List<@NotNull String>> parse(@NotNull final String query)
	{
		Objects.requireNonNull(query);

		Map<String, List<String>> params = new LinkedHashMap<>();

		if (query.length() < 3) {
			return params;
		}

		for (String groupParam : query.split("&"))
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

					if (params.containsKey(paramGroup)) {
						queryParamValues = params.get(paramGroup);
					}
				}

				queryParamValues.add(valueGroup);
				params.put(paramGroup, queryParamValues);
			}
		}

		return params;
	}

	/**
	 * Собирает параметры URI.
	 *
	 * @param params Коллекция параметров и их значений.
	 * @return Строку с закодированными параметрами URI.
	 */
	@NotNull
	public static String compose(@NotNull final Map<@NotNull String, @NotNull List<@NotNull String>> params)
	{
		Objects.requireNonNull(params);

		StringBuilder query = new StringBuilder();

		if (params.size() == 0) {
			return query.toString();
		}

		boolean needSeparator = false;

		for (Map.Entry<String, List<String>> groupParam : params.entrySet())
		{
			Objects.requireNonNull(groupParam.getKey());
			Objects.requireNonNull(groupParam.getValue());

			if (needSeparator) {
				query.append("&");
			}

				for (int elm = 0; elm < groupParam.getValue().size(); elm++)
				{
					Objects.requireNonNull(groupParam.getValue().get(elm));

					if (elm > 0) {
						query.append("&");
					}

					query.append(URLEncoder.encode(groupParam.getKey(), StandardCharsets.UTF_8));

					if (groupParam.getValue().size() > 1) {
						query.append("[]");
					}

					query.append("=");
					query.append(URLEncoder.encode(groupParam.getValue().get(elm), StandardCharsets.UTF_8));
				}

			needSeparator = true;
		}

		return query.toString();
	}
}
