package com.palm.epalm.base.web.sitemap;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 13-6-9
 * Time: 下午7:27
 * -站点导航类，包括权限设置，模块设置，模块归属，菜单等
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement(namespace = "http://www.palmxj.com/xml/config/sitemap", name = "sitemap")
public final class SiteMap {

    private List<Role> roles = new ArrayList<Role>();
    private List<Menus> menus = new ArrayList<Menus>();
    private List<Module> navigation = new ArrayList<Module>();

    @XmlElementWrapper(name = "roles", namespace = "http://www.palmxj.com/xml/config/sitemap")
    @XmlElement(required = true, name = "role", namespace = "http://www.palmxj.com/xml/config/sitemap")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    //@XmlElementWrapper(name = "menus",namespace = "http://www.palmxj.com/xml/config/sitemap")
    @XmlElement(required = true, name = "menus", namespace = "http://www.palmxj.com/xml/config/sitemap")
    //@XmlElement(name="menus")
    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    @XmlElementWrapper(name = "navigation", namespace = "http://www.palmxj.com/xml/config/sitemap")
    @XmlElement(required = true, name = "module", namespace = "http://www.palmxj.com/xml/config/sitemap")
    public List<Module> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<Module> navigation) {
        this.navigation = navigation;
    }
}
