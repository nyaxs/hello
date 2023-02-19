create database  if not exists  hello;


use hello;

drop table if exists bs_message;
create table bs_message (
                            message_id           bigint(20)      not null auto_increment    comment '消息ID',
                            sender_id           bigint(20)      default null               comment '发送用户ID',
                            recipient_id           bigint(20)      default null               comment '接收用户ID',
                            content         varchar(255)     not null                   comment '消息内容',
                            message_type         varchar(2)      default '00'               comment '消息类型（00系统用户）',

                            status            varchar(2)         default '00'                comment '消息状态（00未发送 01已发送 02未读 03已读 04撤销 05删除 ） ',
                            del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
                            message_ip          varchar(128)    default ''                 comment '消息IP',
                            create_by         varchar(64)     default ''                 comment '创建者',
                            create_time       datetime                                   comment '创建时间',
                            remark            varchar(500)    default null               comment '备注',
                            primary key (message_id),index (sender_id)
) engine=innodb  comment = '消息表';


drop table if exists sys_user;
create table sys_user (
                          user_id           bigint(20)      not null auto_increment    comment '用户ID',
                          dept_id           bigint(20)      default null               comment '部门ID',
                          login_name         varchar(50)     not null                   comment '用户账号',
                          nick_name         varchar(50)     not null                   comment '用户昵称',
                          user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
                          email             varchar(50)     default ''                 comment '用户邮箱',
                          telephone       varchar(11)     default ''                 comment '手机号码',
                          gender               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
                          avatar            varchar(100)    default ''                 comment '头像地址',
                          password          varchar(100)    default ''                 comment '密码',
                          status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
                          del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
                          login_ip          varchar(128)    default ''                 comment '最后登录IP',
                          login_date        datetime                                   comment '最后登录时间',
                          create_by         varchar(64)     default ''                 comment '创建者',
                          create_time       datetime                                   comment '创建时间',
                          update_by         varchar(64)     default ''                 comment '更新者',
                          update_time       datetime                                   comment '更新时间',
                          remark            varchar(500)    default null               comment '备注',
                          primary key (user_id),index (login_name)
) engine=innodb  comment = '用户信息表';
