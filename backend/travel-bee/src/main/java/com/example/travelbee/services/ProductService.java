package com.example.travelbee.services;

import com.example.travelbee.entities.Product;
import com.example.travelbee.exceptions.ResourceConflictException;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class);
    private ProductRepository productRepository;

//
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //SAVE
    public Product save(Product product) throws ResourceConflictException {
        if(product.getTitle()==null||product.getAddress()==null||product.getPrice()==null||
        product.getCity()==null||product.getCategory()==null||product.getLongitude()==null||
        product.getLatitude()==null||product.getPolicies()==null||product.getFeatures()==null){
            throw new ResourceConflictException("There are some null fields");
        }
        if(productRepository.findByTitle(product.getTitle()).isPresent()){
            throw new ResourceConflictException("The product with that name already exists");
        }
        else{
            return productRepository.save(product);

        }
    }

    //LIST ALL
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    //FIND BY ID
    public Optional<Product> findById(Long id) throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product;
        }
        else{
            throw new ResourceNotFoundException("There is no product with id "+id);
        }
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The product with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    //UPDATE
    public Product update(Product product) throws ResourceNotFoundException{
        Optional<Product> productToUpdate = productRepository.findById(product.getId());
        if(productToUpdate.isPresent()){
            return productRepository.save(product);
        }
        else{
            throw new ResourceNotFoundException("The product with id "+product.getId()+" doesn't exist, so It could not be updated");
        }
    }

    //FILTER BY CATEGORY ID
    public List<Product> filterByCategory(Long id) {
        logger.debug("Buscando productos con categoria id: " + id);
        return productRepository.findByCategoryId(id);
    }

    //FILTER BY CITY ID
    public List<Product> filterByCityId(Long cityId) {
        logger.debug("Buscando productos con ciudad id: " + cityId);
        return productRepository.findByCityId(cityId);
    }

    /*
    FILTER BY CITY NAME
    public List<Product> filterByCityName(String name) {
        logger.debug("Finding products with city name: " + name);
        return productRepository.findByCityName(name);
    }
    */
    //FILTER BY AVAILABLE DATE RANGES
    public List<Product> filterByDates(LocalDate checkin, LocalDate checkout) {
        logger.debug("Finding products with availability from: " + checkin + " to " + checkout);
        return productRepository.filterByAvailableDates(checkin, checkout);
    }


    public List<Product> filterByDatesAndCity(LocalDate checkin, LocalDate checkout, Long cityId) {
        logger.debug("Finding products in "+ cityId + " with availability from: " + checkin + " to " + checkout);
        return productRepository.filterByAvailableDatesAndCityId(checkin, checkout, cityId);
    }


}