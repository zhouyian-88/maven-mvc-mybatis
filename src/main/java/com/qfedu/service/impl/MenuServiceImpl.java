package com.qfedu.service.impl;


import com.qfedu.mapper.MenuMapper;
import com.qfedu.pojo.Menu;
import com.qfedu.service.MenuService;
import com.qfedu.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    private static final SqlSession sqlSession = SqlSessionFactoryUtils.GetSqlSession();
    private static final MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
    @Override
    public List<Menu> getMenuTreeInfo() {

        List<Menu> menus = menuMapper.selectMenuAll(0);


        return menus;
    }
}
