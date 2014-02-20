package com.palm.epalm.base.web.sitemap;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Date: 13-6-14
 * Time: 上午10:39
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement(name = "path", namespace = "http://www.palmxj.com/xml/config/sitemap")
public class Module {
    private String text;
    private String url;
    private String icon;

    private List<Module> children;

    @XmlAttribute(required = true)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement(required = true, name = "module", namespace = "http://www.palmxj.com/xml/config/sitemap")
    public List<Module> getChildren() {
        return children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
