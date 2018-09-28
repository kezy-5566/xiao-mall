package xyz.vimtool.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import xyz.vimtool.bean.Permission;
import xyz.vimtool.response.PermissionRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-27T17:51:46+0800",
    comments = "version: 1.3.0.Beta1, compiler: javac, environment: Java 1.8.0_172 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public PermissionRes toRes(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionRes permissionRes = new PermissionRes();

        permissionRes.setId( permission.getId() );
        permissionRes.setName( permission.getName() );
        permissionRes.setDescription( permission.getDescription() );

        return permissionRes;
    }

    @Override
    public List<PermissionRes> toRes(List<Permission> permissions) {
        if ( permissions == null ) {
            return null;
        }

        List<PermissionRes> list = new ArrayList<PermissionRes>( permissions.size() );
        for ( Permission permission : permissions ) {
            list.add( toRes( permission ) );
        }

        return list;
    }
}
