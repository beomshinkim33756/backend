package com.example.kakao;

import com.example.model.blog.BlogResponseDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.kakao.KakaoBlogApiClientResponseDto;
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
public class KakaoBlogApiClient {

    public BlogResponseDto findBlog(KakaoBlogApiClientRequestDto kakaoBlogApiClientRequestDto) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders(); // 헤더 정보
            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity httpRequestEntity = new HttpEntity<>(headers);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 파라미터 정보

            headers.add("Authorization", kakaoBlogApiClientRequestDto.getToken());
            params.add("query", " " + kakaoBlogApiClientRequestDto.getQuery());
            params.add("sort", kakaoBlogApiClientRequestDto.getSort());
            params.add("page", kakaoBlogApiClientRequestDto.getPage());
            params.add("size", kakaoBlogApiClientRequestDto.getSize());

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(kakaoBlogApiClientRequestDto.getHost() + "/v2/search/blog").queryParams(params); // GET 요청

            log.debug("[카카오 블로그 요청 파라미터 : {}", params);
            ResponseEntity<String> apiResponseJson = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, httpRequestEntity, String.class); // 응답

            if (apiResponseJson.getStatusCode().equals(HttpStatus.OK)) {
                KakaoBlogApiClientResponseDto response = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<KakaoBlogApiClientResponseDto>() {});
                return new BlogResponseDto(response, Integer.parseInt(kakaoBlogApiClientRequestDto.getPage()), Integer.parseInt(kakaoBlogApiClientRequestDto.getSize()));
            } else  {
                log.error("[카카오 블로그 리스트 요청 실패] =========> {} / {} ", apiResponseJson.getStatusCode(), apiResponseJson.getBody());
                return null;
            }
        } catch (Exception e) {
            log.error("[카카오 블로그 리스트 요청 실패] =========>", e);
            return null;
        }

    }
}
