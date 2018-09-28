package xyz.vimtool.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.bean.Role;
import xyz.vimtool.dao.BaseDao;
import xyz.vimtool.dao.RoleDao;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色Service层
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
@Service
public class RoleService extends BaseService<Role, Integer> {

    @Inject
    private RoleDao roleDao;

    @Override
    public BaseDao<Role, Integer> getBaseDao() {
        return this.roleDao;
    }

    /**
     * 根据角色名查找权限
     *
     * @param names 角色名
     * @return 权限
     */
    public Set<String> listPermissions(Collection<String> names) {
        Specification<Role> spec = (root, query, builder) -> {
            CriteriaBuilder.In<Object> in = builder.in(root.get("name"));
            names.forEach(in::value);
            return builder.and(in);
        };
        List<Role> list = super.list(spec);
        Set<String> permissions = new HashSet<>();
        list.forEach(role -> permissions.addAll(
                role.getPermissions()
                        .stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet())
                )
        );
        return permissions;
    }
}
