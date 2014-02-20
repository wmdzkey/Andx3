package com.palm.epalm.base.web.sitemap;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 13-6-27
 * Time: 下午6:49
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement(name = "controlType", namespace = "http://www.palmxj.com/xml/config/sitemap")
public enum ControlType {
    enable, visible
}
