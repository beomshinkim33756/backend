package com.backend.dao;

import com.backend.model.dto.blog.BlogDaoDto;
import com.backend.model.dto.blog.kakao.KakaoBlogRequestDto;
import com.backend.model.dto.blog.kakao.KakaoBlogResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ApiDao {

    public BlogDaoDto findBlogByKakao(KakaoBlogRequestDto kakaoBlogRequestDto) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders(); // 헤더 정보
            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity httpRequestEntity = new HttpEntity<>(headers);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 파라미터 정보

            headers.add("Authorization", kakaoBlogRequestDto.getToken());
            params.add("query", kakaoBlogRequestDto.getQuery());
            params.add("sort", kakaoBlogRequestDto.getSort());
            params.add("page", kakaoBlogRequestDto.getPage());
            params.add("size", kakaoBlogRequestDto.getSize());

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(kakaoBlogRequestDto.getHost() + "/v2/search/blog").queryParams(params); // GET 요청
            ResponseEntity<String> apiResponseJson = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, httpRequestEntity, String.class); // 응답

            if (apiResponseJson.getStatusCode().equals(HttpStatus.OK)) {
                KakaoBlogResponseDto response = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<KakaoBlogResponseDto>() {});
                return new BlogDaoDto(response);
            } else  {
                log.error("[카카오톡 블로그 리스트 요청 실패] =========> {} / {} ", apiResponseJson.getStatusCode(), apiResponseJson.getBody());
                return null;
            }
        } catch (Exception e) {
            log.error("[카카오톡 블로그 리스트 요청 실패] =========> ");
            log.error("{}", e);
            return null;
        }

    }
}
