package com.mouse.study.template.mode.AbstractFactory.Sample.tablefactory;

import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Factory;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Link;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Page;
import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Tray;

public class TableFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new TableLink(caption, url);
    }
    @Override
    public Tray createTray(String caption) {
        return new TableTray(caption);
    }
    @Override
    public Page createPage(String title, String author) {
        return new TablePage(title, author);
    }
}
