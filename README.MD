# URIQuery
Сборщик / разборщик параметров URI.

# Использование
### Сборщик параметров
```java
Map<String, List<String>> queryMap = new LinkedHashMap<>();
queryMap.put("param1", List.of("value1.1"));
queryMap.put("param2", List.of("значение2.1"));
queryMap.put("param3", List.of("value3.1", "value3.2"));
queryMap.put("param4", List.of("value4.1"));

UriQuery.compose(queryMap);
/*
    Результат выполнения:
    param1=value1.1&param2=%D0%B7%D0%BD%D0%B0%D1%87%D0%B5%D0%BD%D0%B8%D0%B52.1&param3[]=value3.1&param3[]=value3.2&param4=value4.1
*/
```

### Разборщик параметров
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

UriQuery.parse(queryString);
/*
    Результат выполнения:
    {
        param1=[value1.1],
        param2=[значение2.1],
        param3=[value3.1, value3.2],
        param4=[value4.1]
    }
*/
```