package xyz.vimtool.mapper;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.vimtool.bean.Role;
import xyz.vimtool.bean.User;
import xyz.vimtool.response.RoleRes;
import xyz.vimtool.response.UserRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-27T17:51:46+0800",
    comments = "version: 1.3.0.Beta1, compiler: javac, environment: Java 1.8.0_172 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserRes toRes(User user) {
        if ( user == null ) {
            return null;
        }

        UserRes userRes = new UserRes();

        userRes.setId( user.getId() );
        userRes.setPhone( user.getPhone() );
        userRes.setName( user.getName() );
        userRes.setRoles( roleSetToRoleResSet( user.getRoles() ) );
        userRes.setCreateTime( user.getCreateTime() );

        return userRes;
    }

    protected Set<RoleRes> roleSetToRoleResSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleRes> set1 = new HashSet<RoleRes>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleMapper.toRes( role ) );
        }

        return set1;
    }
}
