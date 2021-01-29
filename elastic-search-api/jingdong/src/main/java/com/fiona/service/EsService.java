package com.fiona.service;


import com.alibaba.fastjson.JSON;
import com.fiona.pojo.Item;
import com.fiona.utils.HtmlParseUtil;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class EsService {

   @Autowired
   private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HtmlParseUtil parseJson;

    //把网上爬下的数据放入es
    public boolean bulkRequest(String keyword) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("30s");

        List<Item> items = parseJson.parseJson(keyword);

        for (int i = 0; i < items.size(); i++) {
            bulkRequest.add(
               new IndexRequest("jing_dong").source(JSON.toJSONString(items.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return  !bulk.hasFailures();

    }

    //用es查询
    //返回JSON
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
          if(pageNo <= 1){
              pageNo = 1;
          }
        //条件搜索
        SearchRequest jing_dongRequest = new SearchRequest("jing_dong");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        //精确匹配-> QueryBuilders
        TermQueryBuilder title = QueryBuilders.termQuery("title", keyword);
        searchSourceBuilder.query(title);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //执行搜索
        jing_dongRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(jing_dongRequest, RequestOptions.DEFAULT);

        //解析数据
        ArrayList<Map<String, Object>> jsonList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();//再把map返回去
            jsonList.add(map);
        }
        return jsonList;
    }



}
