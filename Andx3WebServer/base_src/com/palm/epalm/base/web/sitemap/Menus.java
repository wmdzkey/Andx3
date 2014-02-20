package com.palm.epalm.base.web.sitemap;

import com.palm.epalm.base.context.ApplicationHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Date: 13-6-28
 * Time: 下午7:41
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement(name = "menus", namespace = "http://www.palmxj.com/xml/config/sitemap")
public class Menus implements MenuProvider {
    private String id;
    private List<Menu> menus = new ArrayList<Menu>();
    private Map<Integer, List<Menu>> _menus = new HashMap<Integer, List<Menu>>();
    //匿名菜单，未登录时菜单
    private List<Menu> anonymousMenus;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    //@XmlElementWrapper(name = "menu",namespace = "http://www.palmxj.com/xml/config/sitemap")
    @XmlElement(required = true, name = "menu", namespace = "http://www.palmxj.com/xml/config/sitemap")
    public List<Menu> getMenus() {
        UserDetails user = ApplicationHelper.getCurrentUser();
        if (user == null) return getAnonymousMenus();
        Set<String> authorities = new HashSet<String>();
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) user.getAuthorities();
        if (grantedAuthorities == null) {
            return getAnonymousMenus();
        }
        for (GrantedAuthority a : grantedAuthorities) {
            authorities.add(a.getAuthority());
        }
        return getMenuBy(authorities);
    }

    private List<Menu> getMenuBy(Set<String> authorities) {
        if (authorities == null || authorities.size() == 0) {
            return getAnonymousMenus();
        }
        Integer code = authorities.hashCode();
        List<Menu> result = _menus.get(code);
        if (result != null) return result;
        result = filterMenus(menus, authorities);
        _menus.put(code, result);
        return result;
    }

    private List<Menu> getAnonymousMenus() {
        if (anonymousMenus == null) {
            anonymousMenus = filterAnonymousMenus(menus);
        }
        return anonymousMenus;
    }

    private List<Menu> filterAnonymousMenus(List<Menu> menus) {
        List<Menu> result = new ArrayList<Menu>();
        for (Menu m : menus) {
            if (m.getRoles() == null) {
                Menu newMenu = new Menu(m);
                if (newMenu.getChildren() != null) {
                    newMenu.setChildren(filterAnonymousMenus(newMenu.getChildren()));
                }
                result.add(newMenu);
            }
        }
        return result;
    }

    private List<Menu> filterMenus(List<Menu> menus, Set<String> roles) {
        List<Menu> result = new ArrayList<Menu>();
        for (Menu m : menus) {
            if (isVisible(m, roles)) {
                Menu newMenu = new Menu(m);
                if (newMenu.getChildren() != null) {
                    newMenu.setChildren(filterMenus(newMenu.getChildren(), roles));
                }
                result.add(newMenu);
            }
        }
        return result;
    }

    private boolean isVisible(Menu menu, Set<String> roles) {
        String rs = menu.getRoles();
        if (rs == null) return true;
        String[] roles1 = rs.split(",");
        for (String r1 : roles1) {
            if (roles.contains(r1)) return true;
        }
        return false;
    }

}
