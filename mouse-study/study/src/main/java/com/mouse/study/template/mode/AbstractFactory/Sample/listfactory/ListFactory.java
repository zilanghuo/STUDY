package com.mouse.study.template.mode.AbstractFactory.Sample.listfactory;

import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Factory;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Link;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Page;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }
    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }
    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
