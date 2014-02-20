package com.palm.epalm.base.web.sitemap;

import com.palm.epalm.base.context.ApplicationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.*;

/**
 * Date: 13-6-13
 * Time: 下午7:39
 *
 * @author desire
 * @version 1.0
 */
public class SiteMapProvider implements MenuProvider, IModuleProvider {
    private static SiteMapProvider instance;

    public static SiteMapProvider getInstance() {
        if (instance == null) instance = new SiteMapProvider();
        return instance;
    }

    private SiteMap siteMap;
    private Map<String, Menus> menus;
    private Map<String, List<Module>> modules = new HashMap<String, List<Module>>();

    public SiteMapProvider() {
        loadProperties();
        mapModule();
    }

    private void mapModule() {
        for (Module path : siteMap.getNavigation()) {
            List<Module> pathList = new ArrayList<Module>();
            pathList.add(path);
            String uri = path.getUrl();
            if (uri != null) {
                modules.put(path.getUrl(), pathList);
            }
            List<Module> children = path.getChildren();
            if (children != null) {
                mapMdoule(children, pathList);
            }
        }
    }

    private void mapMdoule(List<Module> children, List<Module> pathList) {
        for (Module child : children) {
            List<Module> paths = new ArrayList<Module>();
            paths.addAll(pathList);
            paths.add(child);
            String uri = child.getUrl();
            if (uri != null) {
                if (modules.containsKey(uri)) {
                    String p1 = "";
                    for (Module m : paths) {
                        p1 += m.getText() + ">";
                    }
                    p1 = p1.replaceFirst(">$", "");
                    paths = modules.get(uri);
                    String p2 = "";
                    for (Module m : paths) {
                        p2 += m.getText() + ">";
                    }
                    p2 = p2.replaceFirst(">$", "");
                    throw new RuntimeException("文件sitemap.xml配置错误，出现重复的url配置：\n mdoule '" + p1 + "'与module '" + p2 + "'的URL'" + uri + "'冲突!");
                }
                this.modules.put(uri, paths);
            }
            List<Module> children1 = child.getChildren();
            if (children1 != null) {
                mapMdoule(children1, paths);
            }
        }
    }

    private void loadProperties() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("siteMap.xml");
            JAXBContext atx = JAXBContext.newInstance(SiteMap.class);
            Unmarshaller unmarshaller = atx.createUnmarshaller();
            siteMap = (SiteMap) unmarshaller.unmarshal(input);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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

    private List<Module> findByUri(String uri) {
        return modules.get(uri);
    }

    @Override
    public List<Menu> getMenus() {
        return getMenus("default");
    }

    public List<Menu> getMenus(String id) {
        if (menus == null) {
            List<Menus> menuses = siteMap.getMenus();
            menus = new HashMap<String, Menus>();
            for (Menus m : menuses) {
                menus.put(m.getId(), m);
            }
        }
        Menus ms = menus.get(id);
        return ms == null ? new ArrayList<Menu>() : ms.getMenus();
    }

    @Override
    public List<Module> getCurrentModule() {
        String uri = (String) ApplicationHelper.getCurrentHttpRequest().getAttribute("_uri");
        return findByUri(uri);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Module> getCurrentModule(HttpServletRequest request) {
        String uri = (String) request.getAttribute("_uri");
        return findByUri(uri);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Module> getAllModules() {
        return siteMap.getNavigation();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
