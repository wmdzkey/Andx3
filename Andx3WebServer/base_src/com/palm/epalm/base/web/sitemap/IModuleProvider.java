package com.palm.epalm.base.web.sitemap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Date: 13-6-17
 * Time: 上午10:24
 *
 * @author desire
 * @version 1.0
 */
public interface IModuleProvider {

    public List<Module> getCurrentModule();

    public List<Module> getCurrentModule(HttpServletRequest request);

    public List<Module> getAllModules();
}
