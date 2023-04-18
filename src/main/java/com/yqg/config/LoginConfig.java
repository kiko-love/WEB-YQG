package com.yqg.config;

import com.yqg.inteceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author KIKO
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    /**
     * 跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //指哪些接口URL需要增加跨域设置
        registry.addMapping("/**")
                //.allowedOrigins("*")//指的是前端哪些域名被允许跨域
                .allowedOriginPatterns("*")
                //需要带cookie等凭证时，设置为true，就会把cookie的相关信息带上
                .allowCredentials(true)
                //指的是允许哪些方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //cookie的失效时间，单位为秒（s），若设置为-1，则关闭浏览器就失效
                .maxAge(3600);
    }


    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    };

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加不拦截路径
        String[] excludePathPatters = {
                "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg",
                "/**/*.html", "/login/**", "/register/**", "/api/socket/**","/image/**",
                "/article/list/**"
        };
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatters);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /text 路径映射到 D:/WEB_YQG_STATIC 目录下
        String filePath = "file:D:/WEB_YQG_STATIC/";
        registry.addResourceHandler("/text/**")
                .addResourceLocations(filePath)
                .setCachePeriod(3600);
        // 将 /images 路径映射到 D:/WEB_YQG_STATIC/image 目录下
        registry.addResourceHandler("/image/**")
                .addResourceLocations(filePath + "/image/")
                .setCachePeriod(3600);
        // 将 /videos 路径映射到 D:/WEB_YQG_STATIC/video 目录下
        registry.addResourceHandler("/video/**")
                .addResourceLocations(filePath + "/video/")
                .setCachePeriod(3600);
    }

}

