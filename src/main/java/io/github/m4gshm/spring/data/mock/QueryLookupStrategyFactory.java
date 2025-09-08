package io.github.m4gshm.spring.data.mock;

import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

public interface QueryLookupStrategyFactory {

    QueryLookupStrategyFactory DEFAULT = (key, evaluationContextProvider) -> {
        return new MockQueryLookupStrategy();
    };

    QueryLookupStrategy getQueryLookupStrategy(Key key, QueryMethodEvaluationContextProvider evaluationContextProvider);

}
