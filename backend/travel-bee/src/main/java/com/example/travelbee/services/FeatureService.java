package com.example.travelbee.services;

import com.example.travelbee.entities.Feature;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {
    private FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    //SAVE
    public Feature save(Feature feature) throws ResourceNotFoundException {
        if(feature.getTitle().isEmpty()||feature.getIcon_url().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else if(feature.getTitle()==null||feature.getIcon_url()==null){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return featureRepository.save(feature);
        }
    }


    //FIND BY ID8
    public Optional<Feature> findById(Long id) throws ResourceNotFoundException {
        Optional<Feature> feature = featureRepository.findById(id);
        if(feature.isPresent()){
            return feature;
        }
        else{
            throw new ResourceNotFoundException("There is no feature with id "+id);
        }
    }

    //FIND ALL
    public List<Feature> findAll(){
        return featureRepository.findAll();
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Feature> feature = featureRepository.findById(id);
        if(feature.isPresent()){
            featureRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The feature with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    //UPDATE
    public Feature update(Feature feature) throws ResourceNotFoundException{
        Optional<Feature> featureToUpdate = featureRepository.findById(feature.getId());
        if(featureToUpdate.isPresent()){
            return featureRepository.save(feature);
        }
        else{
            throw new ResourceNotFoundException("The feature with id "+feature.getId()+" doesn't exist, so It could not be updated");
        }
    }
}
