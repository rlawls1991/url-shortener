package com.urlshortener.doamin.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.urlshortener.doamin.dto.QUrlShortenerDto;
import com.urlshortener.doamin.dto.UrlShortenerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.urlshortener.doamin.QUrlShortener.urlShortener;

@Repository
@RequiredArgsConstructor
public class UrlShortenerRepositoryCustomImpl implements UrlShortenerRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public UrlShortenerDto findByUrl(String originalUrl) {
        return query.select(
                new QUrlShortenerDto(
                        urlShortener.url,
                        urlShortener.shortKey,
                        urlShortener.searchCount
                ))
                .from(urlShortener)
                .where(urlEq(originalUrl))
                .fetchOne();
    }

    private BooleanExpression urlEq(String url) {
        if (url == null || url.equals("")) {
            return null;
        }
        return urlShortener.url.eq(url);
    }

    private BooleanExpression shortKeyEq(String shortKey) {
        if (shortKey == null || shortKey.equals("")) {
            return null;
        }
        return urlShortener.shortKey.eq(shortKey);
    }
}
