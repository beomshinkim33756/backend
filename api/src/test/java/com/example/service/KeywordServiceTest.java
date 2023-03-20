package com.example.service;

import com.example.enums.SortType;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class KeywordServiceTest {

    @Autowired
    private ApiService apiService;

    @Test
    @DisplayName("키워드 증가")
    void keyword_test_1() throws Exception {
        apiService.incrementCount("keyword");
    }

    @Test
    @DisplayName("키워드 랭킹 호출")
    void keyword_test_2() throws Exception {
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank();
        System.out.println(keywordResponseDto);
    }

    @Test
    @DisplayName("키워드 랭킹 동시성 문제")
    void keyword_test_3() throws Exception {
        ArrayList<String> words = new ArrayList<>();

        while (words.size() < 20) {
            String word = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            if (!words.contains(word)) words.add(word);
        }

        for (int i=0; i < 10000; i++) {
            int index = (int)(System.currentTimeMillis() % 20);
            String word = words.get(index);
            apiService.incrementCount(word);
        }

        try {
            Thread.sleep(5000);
            KeywordResponseDto keywordResponseDto = apiService.findKeywordRank();
            System.out.println(keywordResponseDto);
            int count = 0;
            for (int i=0 ; i < keywordResponseDto.getRanks().size(); i++) {
                count += keywordResponseDto.getRanks().get(i).getCount();
            }
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
