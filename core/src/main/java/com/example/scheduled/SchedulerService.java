package com.example.scheduled;

import com.example.repositories.KeywordTbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final KeywordTbRepository keywordTbRepository;

    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Seoul") // 매일 오전 6시
    @Transactional
    public void resetSearchCount() {
        log.debug("[인기 검색어 초기화] ========> ");
        keywordTbRepository.resetSearchCount();
    }
}
