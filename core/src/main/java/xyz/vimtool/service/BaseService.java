package xyz.vimtool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.vimtool.dao.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * service基础封装
 *
 * @author  zhangzheng
 * @version 1.0
 * @since   jdk1.8
 * @date    2018-4-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 获取映射Dao
     *
     * @return Dao
     */
    public abstract BaseDao<T, ID> getBaseDao();

    public T save(T entity) {
        if (entity == null) {
            return null;
        }

        try {
            return getBaseDao().saveAndFlush(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> save(Iterable<T> entities) {
        try {
            return getBaseDao().saveAll(entities);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean exists(ID id) {
        if (id == null) {
            return false;
        }

        try {
            return getBaseDao().existsById(id);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(ID id) {
        if (id == null) {
            return false;
        }

        try {
            getBaseDao().deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(T entity) {
        if (entity == null) {
            return false;
        }

        try {
            getBaseDao().delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Iterable<T> entities) {
        try {
            getBaseDao().deleteInBatch(entities);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public T getById(ID id) {
        if (id == null) {
            return null;
        }

        try {
            return getBaseDao().findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public T getOne(Specification<T> spec) {
        try {
            return getBaseDao().findOne(spec).get();
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> listAll() {
        try {
            return getBaseDao().findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<T> listAll(Sort sort) {
        try {
            return getBaseDao().findAll(sort);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<T> list(Iterable<ID> ids) {
        try {
            return getBaseDao().findAllById(ids);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Page<T> list(Pageable pageable) {
        try {
            return getBaseDao().findAll(pageable);
        } catch (Exception e) {
            return new PageImpl<>(new ArrayList<>());
        }
    }

    public List<T> list(Specification<T> spec) {
        try {
            return getBaseDao().findAll(spec);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<T> list(Specification<T> spec, Sort sort) {
        try {
            return getBaseDao().findAll(spec, sort);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Page<T> list(Specification<T> spec, Pageable pageable) {
        try {
            return getBaseDao().findAll(spec, pageable);
        } catch (Exception e) {
            return new PageImpl<>(new ArrayList<>());
        }
    }

    public long count() {
        try {
            return getBaseDao().count();
        } catch (Exception e) {
            return 0L;
        }
    }

    public long count(Specification<T> spec) {
        try {
            return getBaseDao().count(spec);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 构建排序、分页sql
     */
    protected String buildSqlByPage(Pageable pageable) {
        return buildSqlByPage(pageable, true);
    }

    /**
     * 构建排序、分页sql
     */
    protected String buildSqlByPage(Pageable pageable, boolean isCamelCase) {
        StringBuilder sql = new StringBuilder();
        if (pageable != null) {
            if (pageable.getSort() != null) {
                Iterator<Sort.Order> iterator = pageable.getSort().iterator();
                sql.append(" order by ");
                while (iterator.hasNext()) {
                    Sort.Order order = iterator.next();

                    String property = order.getProperty();
                    if (isCamelCase) {
                        // 驼峰转下划线
                        property = property.replaceAll("([A-Z])", "_$1").toLowerCase();
                    }

                    sql.append(property).append(" ").append(order.getDirection()).append(",");
                }
                sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" limit ").append(pageable.getOffset()).append(",").append(pageable.getPageSize());
        }
        return sql.toString();
    }
}
