package ru.hse.yume.data.mapper;

import org.apache.ibatis.annotations.Select;
import ru.hse.yume.data.entity.ActivityInstance;

import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 11/04/17.
 */
public interface BlogMapper {
    @Select("SELECT * FROM act_hi_actinst")
//    @Results({
//            @Result(property = "id", column = "id_"),
//            @Result(property = "activityId", column = "act_id_")
//    })
    List<ActivityInstance> selectBlog();
}
