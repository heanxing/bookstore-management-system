package com.bookstore.dao;

import com.bookstore.bean.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by heanxing on 2018/10/10.
 */
@Repository
public interface CartDao {
    List<Cart> seleByUser(Long user_id);


    int insert(Cart cart);//加入购物车

    int update(Cart cart);//修改购物车

    int deleteBatch(Long... id);


}
