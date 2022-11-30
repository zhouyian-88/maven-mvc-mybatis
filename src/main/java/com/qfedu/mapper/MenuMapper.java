package com.qfedu.mapper;

import com.qfedu.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-10:12
 */
@Mapper
public interface MenuMapper {
    List<Menu> selectMenuAll(int i);
}
