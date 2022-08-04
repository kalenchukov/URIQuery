# URIQuery

Сборщик / разборщик параметров URI.

## Использование

#### Сборщик параметров

```java
Map<String, String[]> queryMap = new LinkedHashMap<>();
queryMap.put("param1", new String[]{"value1.1"});
queryMap.put("param2", new String[]{"значение2.1"});
queryMap.put("param3", new String[]{"value3.1", "value3.2"});
queryMap.put("param4", new String[]{"value4.1"});
queryMap.put("param5", new String[]{null});

UriQuery.compose(queryMap);
/*
Результат выполнения:
param1=value1.1&param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1&param3[]=value3.1&param3[]=value3.2&param4=value4.1&param5=
*/
```

#### Разборщик параметров

```java
StringBuilder queryString = new StringBuilder();
queryString.append("param1=value1.1");
queryString.append("&");
queryString.append("param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1");
queryString.append("&");
queryString.append("param3[]=value3.1");
queryString.append("&");
queryString.append("param3[]=value3.2");
queryString.append("&");
queryString.append("param4=value4.1");
queryString.append("&");
queryString.append("param5=");

UriQuery.parse(queryString.toString());
/*
Результат выполнения:
{
    param1=[value1.1],
    param2=[значение2.1],
    param3=[value3.1, value3.2],
    param4=[value4.1],
    param5=[]
}
*/
```

## Установка

Для установки этого проекта, введите в терминале:

```bash
git clone https://github.com/kalenchukov/URIQuery.git
```

## Версии

Доступные [версии](https://github.com/kalenchukov/URIQuery/releases).

## Авторы

[Алексей Каленчуков](https://github.com/kalenchukov)

## Лицензия

[MIT](https://opensource.org/licenses/MIT)
