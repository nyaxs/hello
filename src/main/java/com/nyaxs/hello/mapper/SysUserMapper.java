package com.nyaxs.hello.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nyaxs.hello.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
