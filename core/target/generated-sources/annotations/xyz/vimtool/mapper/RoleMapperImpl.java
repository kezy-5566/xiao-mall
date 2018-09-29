package xyz.vimtool.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.bean.Role;
import xyz.vimtool.response.PermissionRes;
import xyz.vimtool.response.RoleRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-29T14:44:24+0800",
    comments = "version: 1.3.0.Beta1, compiler: javac, environment: Java 1.8.0_172 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public RoleRes toRes(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleRes roleRes = new RoleRes();

        roleRes.setId( role.getId() );
        roleRes.setName( role.getName() );
        roleRes.setDescription( role.getDescription() );
        roleRes.setPermissions( permissionSetToPermissionResSet( role.getPermissions() ) );

        return roleRes;
    }

    @Override
    public List<RoleRes> toRes(List<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        List<RoleRes> list = new ArrayList<RoleRes>( roles.size() );
        for ( Role role : roles ) {
            list.add( toRes( role ) );
        }

        return list;
    }

    protected Set<PermissionRes> permissionSetToPermissionResSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionRes> set1 = new HashSet<PermissionRes>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionMapper.toRes( permission ) );
        }

        return set1;
    }
}
