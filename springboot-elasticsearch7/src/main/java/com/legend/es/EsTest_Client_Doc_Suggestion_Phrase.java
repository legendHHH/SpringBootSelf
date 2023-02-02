package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.phrase.DirectCandidateGeneratorBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *  短语建议 Phrase Suggester
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_Suggestion_Phrase {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        String keyword = "lucne and elasticsear rock hello world ";
        SearchRequest searchRequest = new SearchRequest("articles");

        //max_errors 最多可以拼错的terms
        //confidence 限制返回结果数，默认1
        PhraseSuggestionBuilder phraseSuggestionBuilder = new PhraseSuggestionBuilder("body").maxErrors(2).confidence(2).addCandidateGenerator(new DirectCandidateGeneratorBuilder("body").suggestMode("missing"));
        phraseSuggestionBuilder.text(keyword);
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("my-suggestion", phraseSuggestionBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);
        searchSourceBuilder.highlighter(new HighlightBuilder().preTags("<em>").postTags("</em>"));
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
        System.out.println("Suggestion_Phrase结果集列表：" + result2.toString());


        //关闭客户端
        esClient.close();
    }
}
