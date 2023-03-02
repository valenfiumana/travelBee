package com.example.travelbee.services;

import com.example.travelbee.entities.Category;
import com.example.travelbee.entities.City;
import com.example.travelbee.exceptions.GlobalExceptionHandler;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.CityRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private static final Logger logger = Logger.getLogger(CityService.class);
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    //SAVE
    public City save(City city) throws ResourceNotFoundException{
        logger.debug("Saving new city: " + city);
        if(city.getName().isEmpty()||city.getCountry().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else if(city.getName()==null||city.getCountry()==null){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return cityRepository.save(city);
        }
    }

    //FIND ALL
    public List<City> listAll() {
        logger.debug("Cities found: ");
        return cityRepository.findAll();
    }

    //FIND BY ID
    public Optional<City> findById(Long id) throws ResourceNotFoundException {
        Optional<City> city = cityRepository.findById(id);
        logger.debug("Searching city by Id: " + id);
        if (city.isPresent()) {
            logger.debug("The city with id: "+id+" was found");
            return city;

        } else {
            logger.debug("The city with id: "+id+" was NOT found");
            throw new ResourceNotFoundException("The city with id: "+id+" was NOT found");
        }
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<City> city = cityRepository.findById(id);
        if(city.isPresent()){
            cityRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The city with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    //UPDATE
    public City update(City city) throws ResourceNotFoundException{
        Optional<City> cityToUpdate = cityRepository.findById(city.getId());
        if(cityToUpdate.isPresent()){
            return cityRepository.save(city);
        }
        else{
            throw new ResourceNotFoundException("The city with id "+city.getId()+" doesn't exist, so It could not be updated");
        }
    }

    //FIND BY NAME
    public Optional<City> findByName(String name) throws ResourceNotFoundException{
        Optional<City> city = cityRepository.findByName(name);
        logger.debug("Searching city by name: " + name);
        if (city.isPresent()) {
            logger.debug("The city with name: "+name+" was found");
            return city;

        } else {
            logger.debug("The city with name: "+name+" was NOT found");
            throw new ResourceNotFoundException("The city with name: "+name+" was NOT found");
        }
    }



}