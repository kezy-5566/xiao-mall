package xyz.vimtool.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/24
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone", nullable = false, length = 11, unique = true)
    private String phone;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    /**
     * 用户角色集合
     * 这里JoinTable可以不添加，
     * 不添加会根据ManyToMany自己增加中间表；添加时便于自己命名
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
