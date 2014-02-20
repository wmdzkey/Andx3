package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.entity.FavoriteTiezi;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import com.palm.epalm.modules.tiebashenqi.repository.FavoriteTieziDao;
import com.umk.andx3.lib.config.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class FavoriteTieziService extends BaseService<FavoriteTiezi> {

    @Autowired
    TiebaService tiebaService;
    @Autowired
    TieziService tieziService;

    @Autowired
    private FavoriteTieziDao favoriteTieziDao;
    public FavoriteTieziDao repository(){
        return favoriteTieziDao;
    }


    public FavoriteTiezi add(FavoriteTiezi favoriteTiezi) {

        Map<String, Object> favoriteUniqueMap = new HashMap<String, Object>();
        favoriteUniqueMap.put("userId", favoriteTiezi.getUserId());
        favoriteUniqueMap.put("tieziId", favoriteTiezi.getTieziId());

        FavoriteTiezi favoriteTieziInDB = exist(favoriteTiezi, favoriteUniqueMap);
        if(favoriteTieziInDB != null) {
            if(favoriteTieziInDB.getState() == Code.State.Delete) {
                favoriteTieziInDB.setState(Code.State.Normal);
                repository().saveAndFlush(favoriteTieziInDB);
            }
            return favoriteTieziInDB;
        } else {
            return repository().save(favoriteTiezi);
        }
    }

    public void cancel(FavoriteTiezi favoriteTiezi) {
        if(favoriteTiezi != null) {
            if(favoriteTiezi.getState() == Code.State.Normal) {
                favoriteTiezi.setState(Code.State.Delete);
                repository().saveAndFlush(favoriteTiezi);
            }
        }
    }

    public List<Tieba> findFavoriteTieba(Long userId) {
        List<Long> favoriteTiebaIdList = favoriteTieziDao.findByHql("select distinct(tiebaId) from FavoriteTiezi where userId = ?", userId);
        return tiebaService.findByIdList(favoriteTiebaIdList);
    }

    public List<Tiezi> findFavoriteTiezi(Long userId, Long tiebaId) {
        List<Long> favoriteTieziIdList = favoriteTieziDao.findByHql("select tieziId from FavoriteTiezi where userId = ? and tiebaId = ?", userId, tiebaId);
        return tieziService.findByIdList(favoriteTieziIdList);
    }

    public List<Tiezi> findAllFavoriteTiezi(Long userId) {
        List<Long> favoriteTieziIdList = favoriteTieziDao.findByHql("select tieziId from FavoriteTiezi where userId = ?", userId);
        return tieziService.findByIdList(favoriteTieziIdList);
    }
}
