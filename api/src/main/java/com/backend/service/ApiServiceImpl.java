package com.backend.service;

import com.backend.dao.ApiDao;
import com.backend.model.dto.blog.BlogDaoDto;
import com.backend.model.dto.blog.BlogServiceDto;
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
    public BlogDaoDto findBlogList(BlogServiceDto blogServiceDto) throws Exception {
        BlogDaoDto blogDaoDto = apiDao.findBlogByKakao(new KakaoBlogRequestDto(blogServiceDto, kakaoBlogProp));
        if (blogDaoDto == null) {
            // 네이버 호출
        }
        return blogDaoDto;
    }
}
