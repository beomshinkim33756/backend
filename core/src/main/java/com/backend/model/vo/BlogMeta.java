package com.backend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogMeta {

    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
}
