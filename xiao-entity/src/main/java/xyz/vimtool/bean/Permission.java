package xyz.vimtool.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 权限
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/21
 */
@Data
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
