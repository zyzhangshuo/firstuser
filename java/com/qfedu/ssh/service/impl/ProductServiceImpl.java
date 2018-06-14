package com.qfedu.ssh.service.impl;


import com.qfedu.ssh.bean.Product;
import com.qfedu.ssh.bean.ProductPage;
import com.qfedu.ssh.dao.ProductDao;
import com.qfedu.ssh.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource(name = "productDao")
    private ProductDao productDao;


    @Override
    public void addProduct(Product product) {
        productDao.insertToProduct(product);
    }

    @Override
    public void removeProduct(Product product) {
        productDao.deleteToProduct(product);

    }

    @Override
    public Product selectOneById(Integer id) {
        return productDao.selectOneById(id);
    }

    @Override
    public void editProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public ProductPage getPage(int pageNo, int pageSize, String category) {
        //业务逻辑需要两个方法的支持
        ProductPage productPage = new ProductPage();
        productPage.setCategory(category);
        productPage.setPageNo(pageNo);
        productPage.setPageSize(pageSize);
        productPage.setProducts(productDao.selectProductByPage(pageNo, pageSize, category));
        productPage.setPageCount((long) Math.ceil(1.0 * productDao.selectProductCount(category) / pageSize));

        return productPage;
    }
}
