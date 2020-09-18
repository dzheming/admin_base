package cn.org.zmabel.admin.entity.mapper;

import cn.org.zmabel.admin.entity.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
 
@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    //返回的Integer值是变化的行数，@Options()会填充到实体 user 中。
    @Insert("insert into user(username, password, role, name, avatar, description, permissions) values(#{username}, #{password}, #{role}, #{name}, #{avatar}, #{description}, #{permissions})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addUser(User user);

    @Update("update user set role = #{role}, name = #{name}, description=#{description}, permissions=#{permissions} where id = #{id}")
    Integer updateUser(User user);

    @Delete("delete from user where id = #{id}")
    Integer deleteUser(Integer id);

    @Select("select * from user")
    List<User> findAllUsers();
}