package com.backend;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CoreTest {
    private String name;

    public CoreTest(String name) {
        this.name = name;
    }
}
