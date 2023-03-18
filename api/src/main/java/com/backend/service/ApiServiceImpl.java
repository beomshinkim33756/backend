package com.backend.service;

import com.backend.dao.ApiDao;
import com.backend.model.dto.BlogDaoDto;
import com.backend.model.dto.BlogServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ApiDao apiDao;
    @Override
    public BlogDaoDto findBlogList(BlogServiceDto blogServiceDto) throws Exception {
        return apiDao.findBlogByKakao(blogServiceDto);
    }
}
