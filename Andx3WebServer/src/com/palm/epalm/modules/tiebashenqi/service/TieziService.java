package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import com.palm.epalm.modules.tiebashenqi.repository.TieziDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TieziService extends BaseService<Tiezi> {

    @Autowired
    private TieziDao tieziDao;
    public TieziDao repository(){
        return tieziDao;
    }


    public Tiezi findByUrl(String url) {
        List<Tiezi> tieziList = tieziDao.findByHql("from Tiezi where url = ?", url);
        if (tieziList != null && tieziList.size() != 0) {
            return tieziList.get(0);
        }
        return null;
    }
}
