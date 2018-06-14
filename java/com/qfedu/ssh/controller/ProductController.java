package com.qfedu.ssh.controller;

import com.google.gson.Gson;
import com.qfedu.ssh.bean.Product;
import com.qfedu.ssh.bean.ProductPage;
import com.qfedu.ssh.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource(name = "productService")
    private ProductService productService;


    @RequestMapping("/showone")
    public String showone(
            @RequestParam("id")
                    Integer id,
            Map<String, Object> map

    ) {
        map.put("id", id);
        return "showone";
    }

    @RequestMapping("/getproductinfobyid")
    public void getproductinfobyid(
            @RequestParam("id")
                    Integer id,
            HttpServletResponse response
    ) throws IOException {
        Product product = productService.selectOneById(id);
        response.getWriter().write(new Gson().toJson(product));

    }

    @RequestMapping("/deleteone")
    public void deleteone(
            @RequestParam("id")
                    Integer id,
            HttpServletResponse response
    ) throws IOException {
        productService.removeProduct(productService.selectOneById(id));
        response.getWriter().write("{\"state\":\"success\"}");
    }

    @RequestMapping("/showedit")
    public String showedit(
            @RequestParam("id")
                    Integer id,
            Map<String, Object> map
    ) {
        map.put("id", id);
        return "showedit";
    }

    @RequestMapping("/edit")
    public void edit(
            @RequestParam("id")
                    Integer id,
            @RequestParam("category")
                    String category,
            @RequestParam("description")
                    String description,
            @RequestParam("name")
                    String name,
            @RequestParam("pnum")
                    String pnum,
            @RequestParam("price")
                    String price,
            HttpServletResponse response
    ) throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setCategory(category);
        product.setDescription(description);
        product.setName(name);
        product.setPnum(Integer.parseInt(pnum));
        product.setPrice(new BigDecimal(price));
        productService.editProduct(product);
        response.getWriter().write("{\"state\":\"success\"}");


    }


    @RequestMapping("/showproduct")
    public String showproduct() {
        return "showproduct";
    }


    @RequestMapping("page")
    public void getPageProduct(
            @RequestParam("pageno")
                    int pageNo,
            @RequestParam("pagesize")
                    int pageSize,
            @RequestParam("category")
                    String category,
            HttpServletResponse response
    ) throws IOException {
       /* response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");*/
        ProductPage page = productService.getPage(pageNo, pageSize, category);
        response.getWriter().write(new Gson().toJson(page));
    }

    @RequestMapping("/showadd")
    public String showadd() {
        return "addproduct";
    }

    @RequestMapping("/add")
    public void add(
            @RequestParam("category")
                    String category,
            @RequestParam("description")
                    String description,
            @RequestParam("name")
                    String name,
            @RequestParam("pnum")
                    String pnum,
            @RequestParam("price")
                    String price,
            HttpServletResponse response
    ) throws IOException {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(description);
        product.setName(name);
        product.setPnum(Integer.parseInt(pnum));
        product.setPrice(new BigDecimal(price));
        productService.addProduct(product);
        response.getWriter().write("{\"state\":\"success\"}");
    }
}
