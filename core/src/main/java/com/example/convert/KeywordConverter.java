package com.example.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class KeywordConverter implements AttributeConverter<String, String> {


    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute.trim(); // 공백 삭제
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
