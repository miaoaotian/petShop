package com.miaoaotian.sys_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.miaoaotian.sys_back.dao.PetMapper;
import com.miaoaotian.sys_back.model.Page;
import com.miaoaotian.sys_back.model.Pet;
import com.miaoaotian.sys_back.model.dto.PetAddDTO;
import com.miaoaotian.sys_back.model.query.Query;
import com.miaoaotian.sys_back.service.PetService;
import com.miaoaotian.sys_back.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
@Slf4j
@Service
public class PetServiceImpl implements PetService {
    private PetMapper petMapper;
    @Autowired
    public void setPetMapper(PetMapper petMapper) {
        this.petMapper = petMapper;
    }
    @Override
    public List<Pet> findAll(Page p, Query query) {
        try(com.github.pagehelper.Page<?> __ = PageHelper.startPage(p.getPageNo(), p.getPageSize())) {
            return petMapper.findAll(query);
        }
    }

    @Override
    public void addPet(PetAddDTO petAddDTO) {
        Pet pet = new Pet();
        pet.setStatus(0);
        pet.setHealthStatus(1);
        String filename = RandomStringUtils.randomAlphanumeric(10) + ".jpg";
        log.info(filename);
        pet.setAvatar(filename);
        pet.setName(petAddDTO.getName());
        pet.setBreed(petAddDTO.getBreed());
        pet.setAge(petAddDTO.getAge());
        pet.setGender(petAddDTO.getGender());
        pet.setPrice(petAddDTO.getPrice());
        pet.setDescription(petAddDTO.getDescription());
        pet.setHealthStatus(1);
        pet.setStatus(0);
        BufferedOutputStream bos = null;
        try {
            File file = new File(Constant.FILE_UPLOAD_PATH + filename);
            String base64Image = petAddDTO.getPhoto().split(",")[1];
            byte[] bytes = Base64.getDecoder().decode(base64Image);
            log.info(file.getAbsolutePath());
            bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
            bos.write(bytes);
            petMapper.insert(pet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }  finally {
            if(bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void updatePet(Pet pet) {
        if(pet.getPhoto() != null) {
            String filename = pet.getAvatar();
            String filePath = Constant.FILE_UPLOAD_PATH + filename;
            String base64Image = pet.getPhoto().split(",")[1];
            byte[] bytes = Base64.getDecoder().decode(base64Image);
            BufferedOutputStream bos = null;
            try {
                File file = new File(filePath);
                bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
                bos.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        petMapper.update(pet);
    }

    @Override
    public void deletePets(Integer[] ids) {
        petMapper.deleteByIds(ids);
    }

    @Override
    public List<Pet> findAllPets() {
        return petMapper.findAllPets();
    }
}
