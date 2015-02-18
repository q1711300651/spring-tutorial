package my.spring.aspects.introductions.impl;

import my.spring.aspects.introductions.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ResourceImpl implements Resource {

    private String content;

    @Override
    public void setContent( String content ) {
        this.content=content;
        System.out.println("Установлен контент : " + content);
    }

    @Override
    public String getContent() {
        return content;
    }
}
