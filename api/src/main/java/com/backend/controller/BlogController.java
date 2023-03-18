package com.backend.controller;

import com.backend.entities.SearchKeywordTb;
import com.backend.repositories.SearchKeywordTbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BlogController {

    private final SearchKeywordTbRepository searchKeywordTbRepository;

    @PostMapping("/test")
    public ResponseEntity test() {

        SearchKeywordTb test = searchKeywordTbRepository.save(new SearchKeywordTb());

        return ResponseEntity.ok().body(test);
    }

    @PostMapping("/test/{id}")
    public ResponseEntity test2(
            @PathVariable(name = "id") Long id
    ) {
        SearchKeywordTb searchKeywordTb = searchKeywordTbRepository.findById(id).get();

        return ResponseEntity.ok().body(searchKeywordTb);
    }
}
