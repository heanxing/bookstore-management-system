package com.bookstore.dao;

import com.bookstore.bean.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${邹} on 2018/9/11.
 */
@Repository
public interface CategoryDao {
    // 查询所有
    List<Category> select();
    // 增
    int insert(Category category);
    // 删
    int deleteById(Long id);
    // 改
    int updateById(Category category);
    // 模糊查询
    List<Category> listByName(@Param("name") String name);

    List<Category> selectById(Long id);
}
