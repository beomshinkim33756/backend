package com.example.naver;

import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.blog.naver.NaverBlogApiClientRequestDto;
import com.example.model.blog.naver.NaverBlogApiClientResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverBlogApiClient {

    public BlogResponseDto findBlog(NaverBlogApiClientRequestDto naverBlogApiClientRequestDto) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders(); // 헤더 정보
            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity httpRequestEntity = new HttpEntity<>(headers);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 파라미터 정보

            headers.add("X-Naver-Client-Id", naverBlogApiClientRequestDto.getClientId());
            headers.add("X-Naver-Client-Secret", naverBlogApiClientRequestDto.getClientSecret());
            params.add("query", naverBlogApiClientRequestDto.getQuery());
            params.add("sort", naverBlogApiClientRequestDto.getSort());
            params.add("start", naverBlogApiClientRequestDto.getStart());
            params.add("display", naverBlogApiClientRequestDto.getDisplay());

            log.debug("[네이버 블로그 요청 파라미터 : {}", params);
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(naverBlogApiClientRequestDto.getHost() + "/v1/search/blog.json").queryParams(params); // GET 요청
            ResponseEntity<String> apiResponseJson = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, httpRequestEntity, String.class); // 응답
            if (apiResponseJson.getStatusCode().equals(HttpStatus.OK)) {
                NaverBlogApiClientResponseDto response = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<NaverBlogApiClientResponseDto>() {});
                return new BlogResponseDto(response);
            } else  {
                log.error("[네이버 블로그 리스트 요청 실패] =========> {} / {} ", apiResponseJson.getStatusCode(), apiResponseJson.getBody());
                return null;
            }
        } catch (Exception e) {
            log.error("[네이버 블로그 리스트 요청 실패] =========>", e);
            return null;
        }

    }
}
