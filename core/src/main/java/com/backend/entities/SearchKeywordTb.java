package com.backend.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "SearchKeywordTb")
@Table
@Data
public class SearchKeywordTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 관리자 식별자
}
