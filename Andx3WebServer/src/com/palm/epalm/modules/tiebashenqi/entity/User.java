package com.palm.epalm.modules.tiebashenqi.entity;

import com.palm.epalm.base.repository.IEntity;
import com.umk.andx3.lib.config.Code;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Winnid
 * @title 用户
 * @version:1.0
 * @since：14-01-10
 */
@Entity
@Table(name="user")
public class User extends IEntity implements UserDetails {


    /*****************************************************************************************/
    /**                                      其它信息                                     **/
    /*****************************************************************************************/

    @Column
    private Integer sex;
    @Column
    private String theName;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTheName() {
        return theName;
    }

    public void setTheName(String theName) {
        this.theName = theName;
    }

    /*****************************************************************************************/
    /**                                      基本登陆信息                                     **/
    /*****************************************************************************************/

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /*****************************************************************************************/
    /**                                 implements userDetails                              **/
    /*****************************************************************************************/

    private String roles="ROLE_USER";//所有的人默认含有用户权限

    private List<GrantedAuthority> authorities = null;

    @Transient
    public List<GrantedAuthority> getAuthorities() {
        if(roles == null){
            return  null;
        }
        if(authorities == null){
            genAuthorities();
        }
        return authorities;
    }

    private void genAuthorities(){
        if(roles == null) return;;
        String []rs = roles.split(",");
        authorities = new ArrayList<GrantedAuthority>();
        for(String a:rs){
            authorities.add(new SimpleGrantedAuthority(a));
        }
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

}