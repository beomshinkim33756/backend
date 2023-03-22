package com.example.model.blog.dto;

import com.example.enums.EnterpriseType;
import com.example.enums.PageSize;
import com.example.model.blog.kakao.KakaoBlogApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogApiClientResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
public class BlogResponseDto {

    private List<BlogDocumentDto> documents;
    private Integer totalCount;
    private Integer pageableCount;
    private Boolean isEnd;
    private Integer totalPage;
    private Integer size;
    private Integer page;
    private EnterpriseType enterprise;

    public BlogResponseDto(KakaoBlogApiClientResponseDto kakaoBlogApiClientResponseDto, Integer page, Integer size) {
        this.documents = kakaoBlogApiClientResponseDto.getDocuments().stream().map(it -> new BlogDocumentDto(it)).collect(Collectors.toList());
        this.totalCount = kakaoBlogApiClientResponseDto.getMeta().getTotal_count();
        this.pageableCount = kakaoBlogApiClientResponseDto.getMeta().getPageable_count();
        this.totalPage = setTotalPage(this.pageableCount, size);
        this.isEnd = kakaoBlogApiClientResponseDto.getMeta().getIs_end();
        this.size = size;
        this.page = page;
        this.enterprise = EnterpriseType.KAKAO;
    }

    public BlogResponseDto(NaverBlogApiClientResponseDto naverBlogApiClientResponseDto) {
        this.documents = naverBlogApiClientResponseDto.getItems().stream().map(it -> new BlogDocumentDto(it)).collect(Collectors.toList());
        this.totalCount = naverBlogApiClientResponseDto.getTotal();
        this.pageableCount = naverBlogApiClientResponseDto.getTotal(); // 네이버 API는 총 개수만 전달하므로 총 개수가 사용가능한 페이지 개수로 처리
        this.totalPage = setTotalPage(this.pageableCount, naverBlogApiClientResponseDto.getDisplay());
        this.isEnd = isEndNaver(naverBlogApiClientResponseDto);
        this.size = naverBlogApiClientResponseDto.getDisplay();
        this.page = naverBlogApiClientResponseDto.getStart();
        this.enterprise = EnterpriseType.NAVER;
    }


    private Integer setTotalPage(Integer pageCount, Integer size) { // 총 페이지 계산
        if (pageCount % size == 0) pageCount = pageCount / size;
        else pageCount = pageCount / size + 1;
        return pageCount > PageSize.MAX_PAGE.getCode() ? PageSize.MAX_PAGE.getCode() : pageCount;
    }

    private boolean isEndNaver(NaverBlogApiClientResponseDto naverBlogApiClientResponseDto) { // 네이버 end페이지 검사
        Integer curCount = naverBlogApiClientResponseDto.getDisplay() * naverBlogApiClientResponseDto.getStart(); // 게시판 현재 개수
        // 페이지 * 사이즈 > 전체 개수 보다 큰 경우 마지막 페이지 || 라스트 페이지 50 고정
        return curCount >= naverBlogApiClientResponseDto.getTotal() || naverBlogApiClientResponseDto.getStart() == PageSize.MAX_PAGE.getCode();
    }

}
