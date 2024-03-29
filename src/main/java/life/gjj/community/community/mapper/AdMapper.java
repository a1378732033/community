package life.gjj.community.community.mapper;

import java.util.List;
import life.gjj.community.community.model.Ad;
import life.gjj.community.community.model.AdExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AdMapper {
    long countByExample(AdExample example);

    int deleteByExample(AdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ad record);

    int insertSelective(Ad record);

    List<Ad> selectByExampleWithRowbounds(AdExample example, RowBounds rowBounds);

    List<Ad> selectByExample(AdExample example);

    Ad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ad record, @Param("example") AdExample example);

    int updateByExample(@Param("record") Ad record, @Param("example") AdExample example);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);
}