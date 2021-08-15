# URIQuery
Сборщик / разборщик параметров URI.

### Пример использования
```java
String uriQueryLine = "param1=value1.1&param2=value2.1&param3[]=value3.1&param3[]=value3.2&param4=value4.1";

// Вызов разборщика параметров
UriQuery.parse(uriQueryLine);
/*
    Результат выполнения:
    {
        param1=[value1.1], 
        param2=[value2.1], 
        param3=[value3.1, value3.2], 
        param4=[value4.1]
    }
*/
    
Map<String, List<String>> uriQueryMap = new LinkedHashMap<>(
    Map.ofEntries(
        Map.entry("param1", List.of("value1.1")),
        Map.entry("param2", List.of("value2.1")),
        Map.entry("param3", List.of("value3.1", "value3.2")),
        Map.entry("param4", List.of("value4.1"))
    )
);

// Вызов сборщика параметров
UriQuery.compose(uriQueryMap);
/*
    Результат выполнения: param2=value2.1&param3[]=value3.1&param3[]=value3.2&param4=value4.1&param1=value1.1
*/
```
