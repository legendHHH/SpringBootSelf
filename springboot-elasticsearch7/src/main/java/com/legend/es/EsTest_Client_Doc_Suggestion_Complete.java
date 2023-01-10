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

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索建议
 * suggest search（completion suggest）：就是建议搜索或称为搜索建议，也可以叫做自动完成-auto completion。类似百度中的搜索联想提示功能。
 * 需要使用suggest的时候，必须在定义index时，为其mapping指定开启suggest。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_Suggestion_Complete {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        String keyword = "西游记";
        SearchRequest searchRequest = new SearchRequest("movie");
        //自动完成功能，用户每输入一个字符。就需要即时发送一个查询请求到后端查找匹配项。
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder("title.suggest").prefix(keyword).size(10);
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("title.suggest", completionSuggestionBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();

        //结果列表
        List<String> result = new ArrayList<>();
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = suggest.getSuggestion("title.suggest");
        List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries = suggestion.getEntries();
        //处理结果
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> op : entries) {
            List<? extends Suggest.Suggestion.Entry.Option> options = op.getOptions();
            for (Suggest.Suggestion.Entry.Option pp : options) {
                result.add(pp.getText().toString());
            }
        }
        System.out.println("Suggestion_Complete结果集列表：" + result.toString());


        //关闭客户端
        esClient.close();
    }
}
