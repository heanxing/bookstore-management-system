package com.bookstore.controller;

import com.bookstore.bean.*;
import com.bookstore.common.UserManager;
import com.bookstore.enums.OrderEnum;
import com.bookstore.service.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by heanxing on 2018/9/25.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController  extends BaseApiController{
    @Autowired
    OrderService orderService;


    @GetMapping("/select")
    public Map<String, Object> select(@RequestParam(defaultValue = "1") Integer page_num, @RequestParam(defaultValue = "10") Integer page_size){
        PageHelper.startPage(page_num, page_size);
        return onDataResp(new MyPageInfo<Order>(orderService.select()));
    }
    @GetMapping ("/selectlist")
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer page_num, @RequestParam(defaultValue = "10") Integer page_size) {
        PageHelper.startPage(page_num, page_size);
        return onDataResp(new MyPageInfo<Order>(orderService.selectList()));
    }

    @GetMapping("/selectByOrderId")
    public MyPageInfo<Order> selectByOrderId (@RequestParam(required = true,defaultValue = "1") Integer pageNo, @RequestParam(required = false, defaultValue = "10") Integer pageSize,@RequestParam Long id){
        PageHelper.startPage(pageNo,pageSize);
        return new MyPageInfo<Order>(orderService.selectByOrderId(id));
    }

    @GetMapping("/selectByUserId")
    public Map<String,Object> selectByUserId (@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request){
        User currentUser = UserManager.getUser(request);
        PageHelper.startPage(pageNo,pageSize);
        return onDataResp(new MyPageInfo<Order>(orderService.selectByUserId(currentUser.getId())));
    }

    @GetMapping("/selectById")
    public Map<String,Object> selectById(@RequestParam Long id){
       return onDataResp(orderService.selectById(id));
    }

    @PostMapping("/insert")
    public Map<String,Object> insert(@RequestParam Long user_id,@RequestParam String address,@RequestParam String receivre,
                                     @RequestParam String mobile, @RequestParam String order_code,
                                     @RequestParam String user_message ) {
        if (user_message != null && user_message.trim().length() == 0) return onBadResp("联系人不能为空");
        if (address != null && address.trim().length() == 0) return onBadResp("收货地址不能为空");
        if (receivre != null && receivre.trim().length() == 0) return onBadResp("收货人不能为空");
        if (mobile != null && mobile.trim().length() == 0) return onBadResp("联系电话不能为空");
        if (order_code != null && order_code.trim().length() == 0) return onBadResp("订单编号不能为空");

        Order order = new Order();
        OrderItem orderItem= new OrderItem();
        order.setUser_id(user_id);
        order.setOrder_code(order_code);
        order.setAddress(address);
        order.setReceivre(receivre);
        order.setMobile(mobile);
        order.setUser_message(user_message);
        order.setCreate_date(new Date());
        order.setOrderEnum(OrderEnum.PAYMENT);
        if (orderService.insert(order) > 0) return onSuccessRep("添加成功");
        return onBadResp("添加失败");
    }

    //支付成功
    @PostMapping("/payment")
    public Map<String,Object> payment(@RequestParam Long id){

        Order order=new Order();
        order.setId(id);
        order.setPay_date(new Date());
        order.setOrderEnum(OrderEnum.DELIVER);
        if (orderService.updateById(order)>0) return onSuccessRep("支付成功");
        return onBadResp("支付失败");
    }

    //发货
    @GetMapping("/delivery")
    public Map<String,Object> shipments(@RequestParam Long id){

        Order order=new Order();
        order.setId(id);
        order.setDelivery_date(new Date());
        order.setOrderEnum(OrderEnum.RECEVICE);
        if (orderService.updateById(order)>0) return onSuccessRep("发货成功");
        return onBadResp("发货失败");
    }

    @PostMapping("/confirm")
    public Map<String,Object> confirm(@RequestParam Long id){

        Order order=new Order();
        order.setId(id);
        order.setConfirm_date(new Date());
        order.setOrderEnum(OrderEnum.EVALUATE);
        if (orderService.updateById(order)>0) return onSuccessRep("收货成功");
        return onBadResp("收货失败");
    }

    @PostMapping("/cancel")
    public Map<String,Object> cancel (@RequestParam Long[] id){
        orderService.deleteBatch(id);
        return onSuccessRep("删除成功");


    }
}
