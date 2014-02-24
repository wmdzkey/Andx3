package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.entity.Advice;
import com.palm.epalm.modules.tiebashenqi.repository.AdviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdviceService extends BaseService<Advice> {

    @Autowired
    private AdviceDao adviceDao;
    public AdviceDao repository(){
        return adviceDao;
    }

}
