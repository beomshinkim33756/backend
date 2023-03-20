package com.example.entities;

import com.example.convert.KeywordConverter;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "KeywordTb")
@Table
@Data
public class KeywordTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 관리자 식별자

    @Column(name = "keyword")
    @Convert(converter = KeywordConverter.class)
    private String keyword;

    @Column(name = "count")
    private Long count;
}
