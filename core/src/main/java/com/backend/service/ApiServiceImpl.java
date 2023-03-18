package com.backend.service;

import com.backend.model.dto.BlogServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    @Override
    public List findBlogList(BlogServiceDto blogServiceDto) throws Exception {
        return null;
    }
}
