package com.backend.model.dto;

import com.backend.model.vo.Document;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BlogDaoDto {

    private List<Document> documents;
    private Boolean isEnd;
    private Long totalCount;

}
