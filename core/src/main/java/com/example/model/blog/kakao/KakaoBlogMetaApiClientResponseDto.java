package com.example.model.blog.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoBlogMetaApiClientResponseDto {

    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
}
