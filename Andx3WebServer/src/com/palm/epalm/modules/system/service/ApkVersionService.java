package com.palm.epalm.modules.system.service;

import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.system.entity.ApkVersion;
import com.palm.epalm.modules.system.repository.ApkVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Apk版本信息
 */
@Service
@Transactional(readOnly = true)
public class ApkVersionService extends BaseService<ApkVersion> {

    @Autowired
    private ApkVersionDao apkVersionDao;
    public ApkVersionDao repository(){
        return apkVersionDao;
    }

}
