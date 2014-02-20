package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import com.palm.epalm.modules.tiebashenqi.entity.TieziPicture;
import com.palm.epalm.modules.tiebashenqi.repository.TieziDao;
import com.palm.epalm.modules.tiebashenqi.repository.TieziPictureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TieziPictureService extends BaseService<TieziPicture> {

    @Autowired
    private TieziPictureDao tieziPictureDao;
    public TieziPictureDao repository(){
        return tieziPictureDao;
    }

    public List<TieziPicture> findTieziPicture(Long tieziId) {
        List<QueryFilter> queryFilterList = new ArrayList<QueryFilter>();
        queryFilterList.add(new QueryFilter("tieziId", tieziId, MatchType.EQ));
        return findAll(queryFilterList);
    }
}
