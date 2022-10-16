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

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Класс содержит статические методы для сборки / разборки параметров URI.
 */
public final class UriQuery
{
	/**
	 * Конструктор для {@code UriQuery}.
	 */
	private UriQuery() {}

	/**
	 * Разбирает параметры URI.
	 *
	 * @param uri URI.
	 * @return Коллекцию параметров и их значений.
	 */
	@NotNull
	public static Map<@NotNull String, @NotNull String @NotNull []> parse(@NotNull final URI uri)
	{
		Objects.requireNonNull(uri);

		final String query = uri.getRawQuery();

		final Map<String, String[]> params = new LinkedHashMap<>();

		if (query == null || query.isEmpty()) {
			return params;
		}

		for (String groupParam : query.split("&"))
		{
			Pattern pattern = Pattern.compile("(?<param>[a-z0-9_\\-.+,|:]+(\\[\\])?)=(?<value>.*)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(groupParam);

			if (matcher.matches())
			{
				String name = matcher.group("param").toLowerCase();
				String value = URLDecoder.decode(matcher.group("value"), StandardCharsets.UTF_8);

				List<String> values = new ArrayList<>();

				if (name.endsWith("[]"))
				{
					name = name.replace("[]", "");

					if (params.containsKey(name)) {
						values.addAll(Arrays.asList(params.get(name)));
					}
				}

				values.add(value);

				params.put(name, values.toArray(String[]::new));
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
	public static String compose(@NotNull final Map<@NotNull String, @Nullable String @NotNull []> params)
	{
		Objects.requireNonNull(params);

		final StringBuilder query = new StringBuilder();

		if (params.isEmpty()) {
			return query.toString();
		}

		boolean needSeparator = false;

		for (Map.Entry<String, String[]> groupParam : params.entrySet())
		{
			Objects.requireNonNull(groupParam.getKey());
			Objects.requireNonNull(groupParam.getValue());

			if (needSeparator) {
				query.append("&");
			}

				for (int elm = 0; elm < groupParam.getValue().length; elm++)
				{
					if (elm > 0) {
						query.append("&");
					}

					query.append(URLEncoder.encode(groupParam.getKey(), StandardCharsets.UTF_8));

					if (groupParam.getValue().length > 1) {
						query.append("[]");
					}

					query.append("=");

					if (groupParam.getValue()[elm] != null) {
						query.append(URLEncoder.encode(groupParam.getValue()[elm], StandardCharsets.UTF_8));
					}
				}

			needSeparator = true;
		}

		return query.toString();
	}
}
