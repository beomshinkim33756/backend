package com.backend.service;

import com.backend.dao.ApiDao;
import com.backend.model.dto.blog.BlogDto;
import com.backend.model.dto.blog.BlogRequestDto;
import com.backend.model.dto.blog.kakao.KakaoBlogRequestDto;
import com.backend.prop.KakaoBlogProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ApiDao apiDao;
    private final KakaoBlogProp kakaoBlogProp;
    @Override
    public BlogDto findBlogList(BlogRequestDto blogRequestDto) throws Exception {
        BlogDto blogDto = apiDao.findBlogByKakao(new KakaoBlogRequestDto(blogRequestDto, kakaoBlogProp));
        if (blogDto == null) {
            // 네이버 호출
        }
        return blogDto;
    }
}
