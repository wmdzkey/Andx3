package com.palm.epalm.base.web.sitemap;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Date: 13-6-14
 * Time: 上午10:35
 *
 * @author desire
 * @version 1.0
 */
@XmlRootElement(name = "role")
public class Role {
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    // @JacksonXmlText
    @XmlValue
    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        this.role = value;
    }
}
