package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.repository.TiebaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TiebaService extends BaseService<Tieba> {

    @Autowired
    private TiebaDao tiebaDao;
    public TiebaDao repository(){
        return tiebaDao;
    }

}
