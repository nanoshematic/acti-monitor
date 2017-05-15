package ru.hse.yume.data.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Alexey Batrakov
 * Date: 15/04/17.
 */
public class AbstractDao {
    static SqlSessionFactory sqlSessionFactory;
    static {
        String resource = "ru/hse/yume/data/config/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    public static SqlSessionFactory getSessionFactory() {
        return sqlSessionFactory;
    }
}
