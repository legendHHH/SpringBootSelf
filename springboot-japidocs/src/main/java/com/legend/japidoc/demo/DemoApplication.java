package com.legend.japidoc.demo;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author legend
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        DocsConfig config = new DocsConfig();
        // root project path
        config.setProjectPath("D:\\WorkCode\\ideaWork\\SpringBootSelf\\springboot-japidocs\\src");
        // project name
        config.setProjectName("ProjectName");
        // api version
        config.setApiVersion("V1.0");
        // api docs target path
        config.setDocsPath("D:\\WorkCode\\ideaWork\\SpringBootSelf\\springboot-japidocs\\src\\doc");
        // auto generate
        config.setAutoGenerate(Boolean.TRUE);
        // execute to generate
        Docs.buildHtmlDocs(config);
    }

}
