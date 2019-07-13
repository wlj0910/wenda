package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserDAO {
    String TABLE_NAME=" user ";
    String INSERT_FIELDS=" name,password,salt,head_url ";
    String SELEST_FIELDS=" id, "+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);
}
