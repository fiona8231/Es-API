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
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Highlighter;
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


        //高亮效果
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        jing_dongRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(jing_dongRequest, RequestOptions.DEFAULT);


        //解析数据
        ArrayList<Map<String, Object>> jsonList = new ArrayList<>();

        //结果要封装成高亮
        for (SearchHit hit : searchResponse.getHits()) {

            //获取高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField t= highlightFields.get("title");

            Map<String, Object> map = hit.getSourceAsMap();
            //如果高亮字段存在，替换一下原来的字符串
            if(t != null){
                Text[] texts = t.getFragments();//convert to text
                StringBuilder new_title = new StringBuilder();
                for (Text text : texts) {
                    new_title.append(text);
                }
                //再把高亮字段返回去map
                map.put("title", new_title.toString());
            }

            jsonList.add(map);
        }
        return jsonList;
    }



}
