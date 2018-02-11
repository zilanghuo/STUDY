package com.mouse.study.template.mode.AbstractFactory.Sample.tablefactory;

import com.mouse.study.template.mode.AbstractFactory.Sample.factory.Link;

public class TableLink extends Link {
    public TableLink(String caption, String url) {
        super(caption, url);
    }
    @Override
    public String makeHTML() {
        return "<td><a href=\"" + url + "\">" + caption + "</a></td>\n";
    }
}
