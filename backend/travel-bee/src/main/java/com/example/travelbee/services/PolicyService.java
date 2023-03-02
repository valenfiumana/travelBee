package com.example.travelbee.services;

import com.example.travelbee.entities.Feature;
import com.example.travelbee.entities.Policy;
import com.example.travelbee.exceptions.ResourceNotFoundException;
import com.example.travelbee.repositories.FeatureRepository;
import com.example.travelbee.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    private PolicyRepository policyRepository;

    @Autowired
    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    //SAVE
    public Policy save(Policy policy) throws ResourceNotFoundException {
        if(policy.getDescription().isEmpty()||policy.getType().isEmpty()){
            throw new ResourceNotFoundException("There are some fields empty");
        }
        else if(policy.getDescription()==null||policy.getType()==null){
            throw new ResourceNotFoundException("There are some null fields");
        }
        else{
            return policyRepository.save(policy);
        }
    }


    //FIND BY ID8
    public Optional<Policy> findById(Long id) throws ResourceNotFoundException {
        Optional<Policy> policy = policyRepository.findById(id);
        if(policy.isPresent()){
            return policy;
        }
        else{
            throw new ResourceNotFoundException("There is no policy with id "+id);
        }
    }

    //FIND ALL
    public List<Policy> findAll(){
        return policyRepository.findAll();
    }

    //DELETE
    public void delete(Long id) throws ResourceNotFoundException{
        Optional<Policy> policy = policyRepository.findById(id);
        if(policy.isPresent()){
            policyRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("The policy with id "+id+" doesn't exist, so It could not be deleted");
        }
    }

    //UPDATE
    public Policy update(Policy feature) throws ResourceNotFoundException{
        Optional<Policy> policyToUpdate = policyRepository.findById(feature.getId());
        if(policyToUpdate.isPresent()){
            return policyRepository.save(feature);
        }
        else{
            throw new ResourceNotFoundException("The policy with id "+feature.getId()+" doesn't exist, so It could not be updated");
        }
    }
}
