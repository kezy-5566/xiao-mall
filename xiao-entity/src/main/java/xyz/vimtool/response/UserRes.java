package xyz.vimtool.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户Response
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/26
 */
@Data
public class UserRes {

    private Integer id;

    private String phone;

    private String name;

    private Set<RoleRes> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime = LocalDateTime.now();

    private String token;
}
