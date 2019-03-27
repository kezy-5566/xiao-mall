package xyz.vimtool.service;

import org.springframework.stereotype.Service;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.dao.BaseDao;
import xyz.vimtool.dao.PermissionDao;

import javax.inject.Inject;

/**
 * 权限Service层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
@Service
public class PermissionService extends BaseService<Permission, Integer> {

    @Inject
    private PermissionDao permissionDao;

    @Override
    public BaseDao<Permission, Integer> getBaseDao() {
        return this.permissionDao;
    }
}
