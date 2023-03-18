package com.backend.dao;

import com.backend.model.dto.BlogDaoDto;
import com.backend.model.dto.BlogServiceDto;
import com.backend.prop.KakaoBlogProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ApiDao {

    private final KakaoBlogProp kakaoBlogProp;

    public BlogDaoDto findBlogByKakao(BlogServiceDto blogServiceDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoBlogProp.getKakaoRestKey());
        HttpEntity httpRequestEntity = new HttpEntity<>(headers);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("query", " " +  blogServiceDto.getKeyword());
        params.add("sort", blogServiceDto.getSort());
        params.add("page", blogServiceDto.getPage());
        params.add("size", blogServiceDto.getSize());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoBlogProp.getKakaoBlogUrl() + "/v2/search/blog")
                .queryParam("query", " " +  blogServiceDto.getKeyword());

        ResponseEntity<String> apiResponseJson = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, httpRequestEntity, String.class);

        log.debug("{}", apiResponseJson);

        return null;
    }
}
