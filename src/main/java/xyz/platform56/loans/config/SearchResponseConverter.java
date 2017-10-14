package xyz.platform56.loans.config;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import xyz.platform56.loans.pojo.SearchResponse;

import java.util.List;

@Component
public class SearchResponseConverter {

    public <S, D> SearchResponse<D> buildSearchResponse(SearchResponse<S> sourceResponse, Function<S, D> transformer) {
        SearchResponse<D> targetResponse = new SearchResponse<D>();

        targetResponse.setStart(sourceResponse.getStart() + 1);
        targetResponse.setCount(sourceResponse.getCount());
        targetResponse.setTotal(sourceResponse.getTotal());

        @SuppressWarnings("unchecked")
        List<D> results = Lists.transform(sourceResponse.getResults(), transformer);
        targetResponse.setResults(results);
        return targetResponse;
    }







}