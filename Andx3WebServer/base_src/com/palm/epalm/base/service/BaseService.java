package com.palm.epalm.base.service;

import com.palm.epalm.base.repository.DefaultRepository;
import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 简单的Service实现，提供基本的增删改查功能
 *
 * @author Winnid
 * @since 2014-01-20
 */
public abstract class BaseService<Entity extends Serializable> {
    protected abstract <T extends DefaultRepository<Entity>> T repository();

    public Entity findOne(Long id) {
        return repository().findOne(id);
    }

    public void delete(Long id) {
        repository().delete(id);
    }

    public void delete(Entity entity) {
        repository().delete(entity);
    }

    public void deleteByIds(Collection<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    public void delete(Collection<Entity> entities) {
        repository().delete(entities);
    }

    public Page<Entity> findAll(List<QueryFilter> filters, Pageable pageable) {
        return repository().findAll(filters, pageable);
    }

    public List<Entity> findAll(List<QueryFilter> filters) {
        return repository().findAll(filters);
    }

    public Entity save(Entity entity) {
        return repository().save(entity);
    }



    public Entity addWithNoExist(Entity entity, String uniqueColumn, Object uniqueValue) {
        Entity entityInDB = exist(entity, uniqueColumn, uniqueValue);
        if(entityInDB != null) {
            return entityInDB;
        } else {
            return repository().save(entity);
        }
    }

    public Entity exist(Entity entity, String uniqueColumn, Object uniqueValue) {
        List<QueryFilter> queryFilterList = new ArrayList<QueryFilter>();
        QueryFilter uniqueQueryFilter = new QueryFilter(uniqueColumn, uniqueValue, MatchType.EQ);
        queryFilterList.add(uniqueQueryFilter);
        List<Entity> entities = repository().findAll(queryFilterList);
        if(entities != null && entities.size() != 0) {
            return entities.get(0);
        }
        return null;
    }

    public Entity addWithNoExist(Entity entity, Map<String, Object> uniqueMap) {
        Entity entityInDB = exist(entity, uniqueMap);
        if(entityInDB != null) {
            return entityInDB;
        } else {
            return repository().save(entity);
        }
    }

    public Entity exist(Entity entity, Map<String, Object> uniqueMap) {
        List<QueryFilter> queryFilterList = new ArrayList<QueryFilter>();
        for (Map.Entry<String, Object> entry : uniqueMap.entrySet()) {
            QueryFilter uniqueQueryFilter = new QueryFilter(entry.getKey(), entry.getValue(), MatchType.EQ);
            queryFilterList.add(uniqueQueryFilter);
        }
        List<Entity> entities = repository().findAll(queryFilterList);
        if(entities != null && entities.size() != 0) {
            return entities.get(0);
        }
        return null;
    }

    public List<Entity> findByIdList(List<Long> idList) {
        List<Entity> entityList = new ArrayList<Entity>();
        if(idList != null && idList.size() != 0) {
            List<QueryFilter> queryFilterList = new ArrayList<QueryFilter>();
            queryFilterList.add(new QueryFilter("id", idList, MatchType.IN));
            entityList = findAll(queryFilterList);
        }
        return entityList;
    }
}
