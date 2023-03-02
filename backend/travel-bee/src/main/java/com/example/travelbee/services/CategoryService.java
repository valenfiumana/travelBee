package com.example.travelbee.services;

import com.example.travelbee.entities.Category;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) throws ResourceNotFoundException{
        if(category.getName().isEmpty()||category.getImageUrl().isEmpty()||category.getDescription().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else if(category.getName()==null||category.getImageUrl()==null||category.getDescription() == null){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return categoryRepository.save(category);
        }
    }


    public Optional<Category> findById(Long id) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return category;
        }
        else{
            throw new ResourceNotFoundException("There is no category with id "+id);
        }
    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The category with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    public Category update(Category category) throws ResourceNotFoundException{
        Optional<Category> categoryToUpdate = categoryRepository.findById(category.getId());
        if(categoryToUpdate.isPresent()){
            return categoryRepository.save(category);
        }
        else{
            throw new ResourceNotFoundException("The category with id "+category.getId()+" doesn't exist, so It could not be updated");
        }
    }

}
