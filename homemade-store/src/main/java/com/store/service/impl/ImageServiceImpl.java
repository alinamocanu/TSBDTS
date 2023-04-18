package com.store.service.impl;

import com.store.domain.Decoration;
import com.store.repository.DecorationRepository;
import com.store.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final DecorationRepository decorationRepository;

    public ImageServiceImpl(DecorationRepository decorationRepository) {
        this.decorationRepository = decorationRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long decorationId, MultipartFile file) {
        try {
            Decoration decoration = decorationRepository.findDecorationByDecorationId(decorationId);
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
            decoration.setImage(byteObjects);
            decorationRepository.save(decoration);
        } catch (IOException e) {
        }
    }
}