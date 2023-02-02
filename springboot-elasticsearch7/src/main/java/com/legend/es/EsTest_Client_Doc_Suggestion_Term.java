package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索建议
 * 类似Google搜索引擎，我给的是一个错误的单词elasticserch，但引擎友好地给出了搜索建议elasticsearch。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_Suggestion_Term {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        String keyword = "elasticserch";
        SearchRequest searchRequest = new SearchRequest("articles");

        //missing 索引中已经存在，就不提供建议
        //popular 推荐出现频率更加高的词
        //always 无论是否存在，都提供建议
        TermSuggestionBuilder termSuggestionBuilder = new TermSuggestionBuilder("body").suggestMode(TermSuggestionBuilder.SuggestMode.MISSING);
        termSuggestionBuilder.text(keyword);
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("term-suggestion", termSuggestionBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("body", "elasticserch")));
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();

        //结果列表
        List<String> result1 = new ArrayList<>();
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = suggest.getSuggestion("term-suggestion");
        List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries = suggestion.getEntries();
        //处理结果
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> op : entries) {
            List<? extends Suggest.Suggestion.Entry.Option> options = op.getOptions();
            for (Suggest.Suggestion.Entry.Option pp : options) {
                result1.add(String.valueOf(pp.getText()));
            }
        }
        System.out.println("Suggestion_Term结果集列表：" + result1.toString());


        //关闭客户端
        esClient.close();
    }
}
