package com.store.service;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.mapper.DecorationMapper;
import com.store.repository.DecorationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface DecorationService {

    Decoration save(DecorationDto decorationDto);

    Decoration findDecorationByDecorationId(Long decorationId);

    Page<Decoration> getDecorationsBy(String category, String name, boolean order, Pageable pageable);

    void deleteById(Long id);
}
