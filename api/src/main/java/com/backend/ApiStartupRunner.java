package com.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class ApiStartupRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("시작");
        CoreTest coreTest = new CoreTest("돼?");
        log.info("{}", coreTest);
    }
}
