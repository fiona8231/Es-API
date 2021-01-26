package com.fiona;

import com.alibaba.fastjson.JSON;
import com.fiona.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;

import java.io.IOException;

@SpringBootTest
class EsApiApplicationTests {

	@Autowired
	@Qualifier("restHighLevelClient")
	private RestHighLevelClient client ;

	//测试创建索引 PUT kuangshen
    @Test
	void testCreateIndex() throws IOException {
    	//创建索引请求
		CreateIndexRequest request = new CreateIndexRequest("kuangshen");
		//执行请求
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println(createIndexResponse);
	}

   //测试获取索引,判断是否存在
   @Test
	void testExistIndex() throws IOException {
		GetIndexRequest request = new GetIndexRequest("kuangshen");
		boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
		System.out.println(exists); //true
	}

	//测试删除索引
	@Test
	void testDelete() throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest("kuangshen");
		AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
		System.out.println(delete);
	}

    @Test
	void testAddDocument() throws IOException {
		User user = new User("yue", 3);

		//创建请求，链接哪个索引库
		IndexRequest request = new IndexRequest("kuangshen");

		//规则 put /kuangshen/_doc/1
		request.id("1");
		request.timeout(TimeValue.timeValueSeconds(2));

		//将我们的数据放入请求 -》 json
		request.source(JSON.toJSONString(user), XContentType.JSON);

		//客户端发送请求，获取响应结果
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

		System.out.println(indexResponse.toString());
		System.out.println(indexResponse.status());

	}

	//判断文档是否存在 get /kuangshen/_doc/1
	@Test
	void testIsExists() throws IOException {
		GetRequest getRequest = new GetRequest("kuangshen", "2");
		//不获取返回的 _source 上下文
		getRequest.fetchSourceContext(new FetchSourceContext(false));

		boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	//获取文档的信息
	@Test
	void testGetDocument() throws IOException {
		GetRequest getRequest = new GetRequest("kuangshen", "1");
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(getResponse.getSource().toString());//打印文档内容
		System.out.println(getResponse);

	}

	@Test
	//更新文档的信息
	void testUpdateDocument() throws IOException {
		UpdateRequest updateRequest = new UpdateRequest("kuangshen", "1");
        updateRequest.timeout("2s");
		//换新的对象
		User user1 = new User("越的Java", 31);
		updateRequest.doc(JSON.toJSONString(user1), XContentType.JSON);

		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		System.out.println(updateResponse);
	}

	//删除文档记录
	@Test
	void deleteRequest() throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("kuangshen", "14");

		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println(deleteResponse.status());
	}

}
