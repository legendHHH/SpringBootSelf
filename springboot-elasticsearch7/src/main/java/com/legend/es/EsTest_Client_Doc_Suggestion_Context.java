package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.completion.context.CategoryQueryContext;

import java.util.*;

/**
 * 搜索建议 Context Suggester
 * 上下文信息场景
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_Suggestion_Context {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        String keyword = "苹";
        SearchRequest searchRequest = new SearchRequest("comments");

        CompletionSuggestionBuilder phraseSuggestionBuilder = new CompletionSuggestionBuilder("comment_autocomplete").prefix(keyword).contexts(Collections.singletonMap("comment_category", Collections.singletonList(CategoryQueryContext.builder().setCategory("电器商城").build())));
        phraseSuggestionBuilder.prefix(keyword);
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("my-suggestion", phraseSuggestionBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();

        //结果列表
        List<String> result2 = new ArrayList<>();
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = suggest.getSuggestion("my-suggestion");
        List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries = suggestion.getEntries();
        //处理结果
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> op : entries) {
            List<? extends Suggest.Suggestion.Entry.Option> options = op.getOptions();
            for (Suggest.Suggestion.Entry.Option pp : options) {
                result2.add(String.valueOf(pp.getText()));
            }
        }
        System.out.println("Suggestion_Context结果集列表：" + result2.toString());


        //关闭客户端
        esClient.close();
    }
}
