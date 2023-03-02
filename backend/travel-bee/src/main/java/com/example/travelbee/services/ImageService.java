package com.example.travelbee.services;

import com.example.travelbee.entities.Image;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.ImageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private static final Logger logger = Logger.getLogger(ImageService.class);

    //SAVE
    public Image save(Image image) throws ResourceNotFoundException {
        logger.debug("Saving new image: " + image);
        if(image.getTitle().isEmpty()||image.getUrl().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else if(image.getTitle()==null||image.getUrl()==null||image.getproduct_id()==null){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return imageRepository.save(image);
        }
    }

    //FIND ALL
    public List<Image> listAll() {
        logger.debug("Images found: ");
        return imageRepository.findAll();
    }

    //FIND BY ID
    public Optional<Image> findById(Long id) throws ResourceNotFoundException {
        Optional<Image> image = imageRepository.findById(id);
        logger.debug("Searching image by Id: " + id);
        if (image.isPresent()) {
            logger.debug("The image with id: "+id+" was found");
            return image;

        } else {
            logger.debug("The image with id: "+id+" was NOT found");
            throw new ResourceNotFoundException("The image with id: "+id+" was NOT found");
        }
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Image> image = imageRepository.findById(id);
        if(image.isPresent()){
            imageRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The image with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    //UPDATE
    public Image update(Image image) throws ResourceNotFoundException{
        Optional<Image> imageToUpdate = imageRepository.findById(image.getId());
        if(imageToUpdate.isPresent()){
            return imageRepository.save(image);
        }
        else{
            throw new ResourceNotFoundException("The image with id "+image.getId()+" doesn't exist, so It could not be updated");
        }
    }


}
