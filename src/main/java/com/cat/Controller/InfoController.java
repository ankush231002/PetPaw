package com.cat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.cat.service.InfoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.cat.dto.InfoResponseDTO;
import com.cat.dto.UpdateInfoDTO;
import com.cat.dto.UserInfo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/info")
public class InfoController {
    
    @Autowired
    private InfoService infoService;

    @PostMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(
            @RequestPart("userInfoDTO") @Valid UserInfo userInfoDTO,
            @RequestPart("photo") MultipartFile photo
    ){
        String message = infoService.create(userInfoDTO, photo);
        return ResponseEntity.ok(message);
    }

    // ============== PUBLIC ======================

    @GetMapping("get/{publicUrl}")
    public ResponseEntity<UserInfo> getByPetName(@PathVariable String publicUrl){
        UserInfo userInfo = infoService.getByPublicUrl(publicUrl);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping()
    public ResponseEntity<List<InfoResponseDTO>> get(){
        List<InfoResponseDTO> responseList = infoService.get();

        return ResponseEntity.ok(responseList);
    }


    @DeleteMapping("/{publicUrl}")
    public ResponseEntity<String> delete(@PathVariable String publicUrl){
        return ResponseEntity.ok(infoService.delete(publicUrl));
    }

    @PutMapping(value = "/{publicUrl}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InfoResponseDTO> update(
            @PathVariable String publicUrl,
            @RequestPart("updateInfoDTO") UpdateInfoDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return ResponseEntity.ok(infoService.update(publicUrl, dto, photo));
    }
    
}