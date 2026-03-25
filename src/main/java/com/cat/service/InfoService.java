package com.cat.service;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cat.dto.InfoResponseDTO;
import com.cat.dto.UserInfo;
import com.cat.entity.Info;
import com.cat.entity.User;
import com.cat.repository.InfoRepo;
import com.cat.repository.UserRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;

@Service
public class InfoService {

    @Autowired
    private InfoRepo infoRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Cloudinary cloudinary;

    // ========== CREATE ===========
    public String create(UserInfo userInfo, MultipartFile photo) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        // Upload to Cloudinary
        String imageUrl;
        String imagePublicId;
        try {
            Map uploadResult = cloudinary.uploader().upload(
                photo.getBytes(),
                ObjectUtils.asMap("folder", "pet-tags")
            );
            imageUrl = (String) uploadResult.get("secure_url");
            imagePublicId = (String) uploadResult.get("public_id");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo", e);
        }

        // Map DTO to Entity
        Info info = new Info();
        info.setUser(user);
        info.setPetName(userInfo.getPetName());
        info.setOwnerName(userInfo.getOwnerName());
        info.setPhone(userInfo.getPhone());
        info.setImagePath(imageUrl);
        info.setImagePublicId(imagePublicId);

        String randomUrl = UUID.randomUUID().toString();
        info.setPublicUrl(randomUrl);

        infoRepo.save(info);

        return randomUrl;
    }

    // =============== PUBLIC ================

    public UserInfo getByPublicUrl(String publicUrl) {

        Info info = infoRepo.findByPublicUrl(publicUrl)
                .orElseThrow(() -> new RuntimeException("pet not found"));

        UserInfo userInfo = new UserInfo();
        userInfo.setPetName(info.getPetName());
        userInfo.setOwnerName(info.getOwnerName());
        userInfo.setPhone(info.getPhone());
        userInfo.setImagePath(info.getImagePath());

        return userInfo;
    }

    public List<InfoResponseDTO> get() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        List<Info> infoList = infoRepo.findByUser(user);

        List<InfoResponseDTO> responseList = new ArrayList<>();

        for (Info info : infoList) {
            InfoResponseDTO dto = new InfoResponseDTO();
            dto.setPetName(info.getPetName());
            dto.setOwnerName(info.getOwnerName());
            dto.setPhone(info.getPhone());
            dto.setImagePath(info.getImagePath());
            dto.setPublicUrl(info.getPublicUrl());
            responseList.add(dto);
        }

        return responseList;
    }

    @Transactional
    public String delete(String publicUrl) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Info info = infoRepo.findByPublicUrl(publicUrl)
                .orElseThrow(() -> new RuntimeException("pet not found"));

        if (!info.getUser().getUserName().equals(userName)) {
            throw new RuntimeException("this is not your pet");
        }

        try {
            cloudinary.uploader().destroy(info.getImagePublicId(), ObjectUtils.emptyMap());
        } catch (Exception e) {
        }

        // ✅ No local file deletion needed anymore, Cloudinary handles it
        infoRepo.delete(info);

        return "deleted";
    }
}