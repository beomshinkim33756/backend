package com.backend.model.vo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Document {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private Timestamp datetime;

}
