package com.nowcoder.dao;

import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface QuestionDAO {
    String TABLE_NAME=" question ";
    String INSERT_FIELDS=" title,content,user_id,created_date,comment_count ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;

    //注解方式
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ", SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
    Question selectById(int id);

    //xml方式
    List<Question> selectLatestQuestions(@Param("userId")int userId,
                                         @Param("offset")int offset,
                                         @Param("limit")int limit);

}
