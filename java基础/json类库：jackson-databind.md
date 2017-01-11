### jackson-databind

- maven依赖

		<!-- jackson-databind -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.8.5</version>
		</dependency>


- jar包

		jackson-databind-{version}.jar
		jackson-annotation-{version}.jar
		jackson-core-{version}.jar

- jackson处理json数据的3种方式

		流式API：Streaming API，是效率最高的处理方式(开销低、读写速度快，但程序编写复杂度高)
		树模型：Tree Model，是最灵活的处理方式
		数据绑定：Data Binding，是最常用的处理方式

### 流式API处理json数据

Streaming API主要通过**JsonParser**和**JsonGenerator**来处理json数据，位于 *jackson-core.jar* 文件的 *com.fasterxml.jackson.core* 包下；前者是用来解析json数据，后者主要是来生产json数据。

JsonParser和JsonGenerator都是抽象类，都需要通过JsonFactory来实例化相应的对象。

#### JsonGenerator示例

- 实例化JsonGenerator

		// 声明一个jsonFactory实例
		JsonFactory jsonFactory = new JsonFactory();
		jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

		// 构造一个json生成器，指定文件名和编码方式
		JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File(JSON_FILE), JsonEncoding.UTF8);

- 生成json数据

		// 开始构造json对象，相当于在最前面加上"{"
		jsonGenerator.writeStartObject();

		// 开始编写属性,必须配对，一个writeFieldName()开始，紧接着一个writeXXX()方法写值
		jsonGenerator.writeFieldName("name");
		jsonGenerator.writeString("wangdh");

		jsonGenerator.writeFieldName("isMan");
		jsonGenerator.writeBoolean(true);

		jsonGenerator.writeFieldName("age");
		jsonGenerator.writeNumber("25");

		jsonGenerator.writeFieldName("fruit");

		// 开始写数值，相当于"["
		jsonGenerator.writeStartArray();
		jsonGenerator.writeString("apple");
		jsonGenerator.writeString("orange");

		// 结束写数组，相当于"]"
		jsonGenerator.writeEndArray();

		// 每次写一个属性
		jsonGenerator.writeStringField("sex", "man");
		jsonGenerator.writeNumberField("degree", 25);
		jsonGenerator.writeBooleanField("hasLover", true);
		
		// 写数组
		jsonGenerator.writeArrayFieldStart("array");
		jsonGenerator.writeString("apple");
		jsonGenerator.writeString("orange");
		jsonGenerator.writeEndArray();

		// 介绍json对象构造，相当于在末尾加"}"
		jsonGenerator.writeEndObject();

		// 释放jsonGenerator资源
		jsonGenerator.close();

#### JsonParser示例

解析json数据时，还有一个JsonToken对象，{，}，[,],key,value都是单独一个JsonToken，通过遍历该对象，再调用jsonParser的相关方法，可以进行json数据解析。

		// 实例化一个jsonFactory对象
		JsonFactory jsonFactory = new JsonFactory();
		jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

		// 构造jsonParser对象
		JsonParser jsonParser = jsonFactory.createParser(new File(JSON_FILE));
		while (!jsonParser.isClosed()) {
			// {，}，[,],key,value都是单独一个JsonToken
			JsonToken jsonToken = jsonParser.nextToken();
			if (jsonToken == null) {
				break;
			}

			// 判断该jsonToken是否是对象的标识符
			if (jsonToken.equals(JsonToken.START_OBJECT) || jsonToken.equals(JsonToken.END_OBJECT)
					|| jsonToken.equals(JsonToken.START_ARRAY) || jsonToken.equals(JsonToken.END_ARRAY)) {
				continue;
			}

			// 解析key和value
			if (jsonToken.equals(JsonToken.FIELD_NAME)) {
				System.out.print("key=" + jsonParser.getText());
			} else {
				System.out.println(";value=" + jsonParser.getText());
			}
		}

### 树模型处理json数据

		JsonFactory jsonFactory = new JsonFactory();
		JsonNodeFactory jsonNodeFactory = new JsonNodeFactory(false);
		ObjectMapper objectMapper = new ObjectMapper();

		JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File("jsonTree.json"), JsonEncoding.UTF8);

		ObjectNode objectNode = jsonNodeFactory.objectNode();
		objectNode.put("name", "wangdh");

		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeTree(jsonGenerator, objectNode);


### 数据绑定处理json

#### javaBean序列化成json

		// 使用objectMapper将对象转为json
		ObjectMapper mapper = new ObjectMapper();
		
		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(dateFormat);
		
		Country country = new Country();
		country.setBirthDate(new Date());
		country.setCountry_id("13");
		
		// 该配置会格式化json数据
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		// 设置mapper忽略空属性
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		
		// 默认情况下，jackson会使用java属性名称字段作为json的属性名称
		// 可以使用jackson 注解改变json属性的名称
		mapper.writeValue(new File("objectMapper.json"), country);	

#### json数据反序列化成javaBean

		ObjectMapper mapper = new ObjectMapper();
		File json = new File("objectMapper.json");

		// 禁用未知属性打断反序列化功能
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// 格式化时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(dateFormat);

		Country country = mapper.readValue(json, Country.class);
		System.out.println(country.getCountry_id());
		System.out.println(dateFormat.format(country.getBirthDate()));



### jackson常用注解

#### 常用注解

- @JsonProperty

		关联json字段到java属性。可以标记在属性上，或其getter/setter方法，使用该注解可以对字段进行重命名

- @JsonAutoDetect
- @JsonIgnore

		json和java相互转化时，忽略该属性

- @JsonIgnoreProperties

		作用在类上，一次设置多个忽略属性

- @JsonIgnoreType
- @JsonInclude

#### 序列化相关

- @JsonFormat
- @JsonUnwrapped
- @JsonView


> 参考资料：http://blog.csdn.net/xiong9999/article/details/53170331

> jackson所有注解：https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations