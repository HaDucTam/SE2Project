package com.example.se2project.service.impl;

import com.example.se2project.entity.OrderDetail;
import com.example.se2project.entity.Product;
import com.example.se2project.repository.ProductRepository;
import com.example.se2project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long, ProductRepository> implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product add(Product product) {
        Product p = productRepository.save(product);
        return p;
    }

    @Override
    public List<Product> getProductsByProductIdBetween(Long i, Long j) {
        List<Product> productList = productRepository.findProductsByProductIdBetween(i,j);
        return productList;
    }

    @Override
    public List<Product> getProductByCategoryId(Long id) {
        List<Product> productList = productRepository.findAllByCategoryCategoriesId(id);
        return productList;
    }

    @Override
    public List<Product> findProductByName(String keyword) {
        if(keyword != null) {
            return productRepository.findProductsByNameContains(keyword);
        }
        return getProductsByProductIdBetween(Long.valueOf(productRepository.findAll().size() - 5), Long.valueOf(productRepository.findAll().size()));
    }

//    public List<Product> listAllProductBySearching(String keyword, Model model) {
//        if(keyword != null) {
//            return productService.findProductByName(keyword);
//        }
//        return productService.findAll();
//    }

    @Override
    public void update(Product product) {
        for(int i = 0; i < findAll().size();i++) {
            Product p = findAll().get(i);
            if(p.getProductId() == product.getProductId()) {
                findAll().set(i, product);
                break;
            }
        }
    }


}