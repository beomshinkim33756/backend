package com.example.repositories;

import com.example.entities.KeywordTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordTbRepository extends JpaRepository<KeywordTb, Long> {

    KeywordTb findByKeyword(@Param(value = "keyword") String keyword);

    List<KeywordTb> findTop10ByOrderByCountDesc();
}
