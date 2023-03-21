package com.example.repositories;

import com.example.entities.KeywordTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface KeywordTbRepository extends JpaRepository<KeywordTb, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    KeywordTb findByKeyword(@Param(value = "keyword") String keyword);
    List<KeywordTb> findTop10ByOrderByCountDesc();

}
