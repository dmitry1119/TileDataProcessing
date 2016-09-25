package com.tilepay.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.tilepay.core.config.CoreAppConfig;

@Import(CoreAppConfig.class)
@Configuration
@ComponentScan("com.tilepay.web")
@EnableAutoConfiguration
@PropertySource(value = { "classpath:/web-application.properties" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        openBrowser("http://localhost:8888/");
    }

    public static void openBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try {
            if (os.indexOf("win") >= 0) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.indexOf("mac") >= 0) {
                rt.exec("open " + url);
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx" };
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");
                rt.exec(new String[] { "sh", "-c", cmd.toString() });
            }
        } catch (Exception e) {
        }
    }
}