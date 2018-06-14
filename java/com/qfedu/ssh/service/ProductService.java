package com.qfedu.ssh.service;

import com.qfedu.ssh.bean.Product;
import com.qfedu.ssh.bean.ProductPage;

public interface ProductService {
    void addProduct(Product product);

    void removeProduct(Product product);

    //查询一个商品
    Product selectOneById(Integer id);

    //编辑一个商品
    void editProduct(Product product);

    //返回一页数据
    ProductPage getPage(int pageNo, int pageSize, String category);
}
