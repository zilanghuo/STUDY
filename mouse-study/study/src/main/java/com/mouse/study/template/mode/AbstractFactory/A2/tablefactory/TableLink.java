package com.mouse.study.template.mode.AbstractFactory.A2.tablefactory;

import com.mouse.study.template.mode.AbstractFactory.A2.factory.Link;

public class TableLink extends Link {
    public TableLink(String caption, String url) {
        super(caption, url);
    }
    @Override
    public String makeHTML() {
        return "<td><a href=\"" + url + "\">" + caption + "</a></td>\n";
    }
}
