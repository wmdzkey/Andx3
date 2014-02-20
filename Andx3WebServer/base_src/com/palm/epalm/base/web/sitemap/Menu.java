package com.palm.epalm.base.web.sitemap;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Date: 13-6-8
 * Time: 下午7:25
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement
public final class Menu {
    private String text;
    private String url;
    private String roles;
    private String icon;
    private Boolean pjax = false;
    private ControlType control = ControlType.visible;
    private List<Menu> children;

    public Menu(String text, String url, String roles, List<Menu> children) {
        this.text = text;
        this.url = url;
        this.roles = roles;
        this.children = children;
    }

    public Menu(String text, String url, String roles, String icon, Boolean pjax, List<Menu> children) {
        this.text = text;
        this.url = url;
        this.roles = roles;
        this.icon = icon;
        this.pjax = pjax;
        this.children = children;
    }

    public Menu() {

    }

    public Menu(Menu menu) {
        this(menu.getText(), menu.getUrl(), menu.getRoles(), menu.getIcon(), menu.getPjax(), menu.getChildren());
    }

    @XmlAttribute(required = true)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlAttribute
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @XmlElement(required = true, name = "menu", namespace = "http://www.palmxj.com/xml/config/sitemap")
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlAttribute
    public Boolean getPjax() {
        return pjax;
    }

    public void setPjax(Boolean pjax) {
        this.pjax = pjax;
    }

    public ControlType getControl() {
        return control;
    }

    public void setControl(ControlType control) {
        this.control = control;
    }
}
