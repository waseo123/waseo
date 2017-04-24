package test;

import com.waseo.area.bean.Area;
import com.waseo.area.mapper.AreaMapper;
import com.waseo.util.RedisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class AreaTest {

    SqlSessionFactory factory = null;

    public void before() throws Exception {
        String resource = "WEB-INF/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(reader);
    }

    public void get() throws Exception {

        SqlSession sqlSession = factory.openSession();
        AreaMapper areaDaoMapper = sqlSession.getMapper(AreaMapper.class);

        Area area = areaDaoMapper.get("0");
        System.out.println(area);

        sqlSession.commit();
        sqlSession.close();
    }

    public void redisSimpleObjTest() {
        RedisUtils.getInstance().set("001", "邱欢");
        String s = RedisUtils.getInstance().get("001");
        System.out.println(s);
    }

    public void redisCommonObjTest() {
        Area area = new Area("1", "2", "3");

        RedisUtils.getInstance().setObject("1", area);

        Area a = (Area) RedisUtils.getInstance().getObject("1");
        System.out.println(a);
    }

}
