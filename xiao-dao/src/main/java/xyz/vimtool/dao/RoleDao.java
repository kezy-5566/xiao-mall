package xyz.vimtool.dao;

import org.springframework.stereotype.Repository;
import xyz.vimtool.bean.Role;

/**
 * 角色Dao层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
@Repository
public interface RoleDao extends BaseDao<Role, Integer> {
}
