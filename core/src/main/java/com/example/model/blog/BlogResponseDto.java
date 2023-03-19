package com.example.model.blog;

import com.example.enums.PageSize;
import com.example.model.blog.kakao.KakaoBlogApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogApiClientResponseDto;
import com.example.model.blog.vo.BlogDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
public class BlogResponseDto {

    private List<BlogDocument> documents;
    private Integer totalCount;
    private Integer pageableCount;
    private Boolean isEnd;

    public BlogResponseDto(KakaoBlogApiClientResponseDto kakaoBlogApiClientResponseDto) {
        this.documents = kakaoBlogApiClientResponseDto.getDocuments().stream().map(it -> new BlogDocument(it)).collect(Collectors.toList());
        this.totalCount = kakaoBlogApiClientResponseDto.getMeta().getTotal_count();
        this.pageableCount = kakaoBlogApiClientResponseDto.getMeta().getPageable_count();
        this.isEnd = kakaoBlogApiClientResponseDto.getMeta().getIs_end();
    }

    public BlogResponseDto(NaverBlogApiClientResponseDto naverBlogApiClientResponseDto) {
        this.documents = naverBlogApiClientResponseDto.getItems().stream().map(it -> new BlogDocument(it)).collect(Collectors.toList());
        this.totalCount = naverBlogApiClientResponseDto.getTotal();
        this.pageableCount = naverBlogApiClientResponseDto.getTotal(); // 네이버 API는 총 개수만 전달하므로 총 개수가 사용가능한 페이지 개수로 처리
        Integer curCount = naverBlogApiClientResponseDto.getDisplay() * naverBlogApiClientResponseDto.getStart(); // 게시판 현재 개수
        if (curCount >= naverBlogApiClientResponseDto.getTotal() || naverBlogApiClientResponseDto.getStart() == PageSize.MAX_PAGE.getCode()) {
            // 페이지 * 사이즈 > 전체 개수 보다 큰 경우 마지막 페이지 || 라스트 페이지 50 고정
            this.isEnd = true;
        } else {
            this.isEnd = false;
        }

    }

}
