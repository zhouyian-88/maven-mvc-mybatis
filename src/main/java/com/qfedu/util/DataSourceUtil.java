package com.qfedu.util;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceUtil {
    private static Properties p=new Properties();
    static{
        try {
            InputStream is = DataSourceUtil.class.getResourceAsStream("/db.properties");
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource(){
        try {
            return DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
