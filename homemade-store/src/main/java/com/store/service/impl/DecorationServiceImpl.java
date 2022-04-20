package com.store.service.impl;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.exception.ResourceNotFoundException;
import com.store.mapper.DecorationMapper;
import com.store.repository.DecorationRepository;
import com.store.service.DecorationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DecorationServiceImpl implements DecorationService {
    private final DecorationRepository decorationRepository;
    private final DecorationMapper decorationMapper;

    public DecorationServiceImpl(DecorationRepository decorationRepository, DecorationMapper decorationMapper) {
        this.decorationRepository = decorationRepository;
        this.decorationMapper = decorationMapper;
    }

    @Override
    public Decoration save(DecorationDto decorationDto){
        return decorationRepository.save(decorationMapper.mapToEntity(decorationDto));
    }

    @Override
    public Decoration findDecorationByDecorationId(Long decorationId) {
        Optional<Decoration> decorationOptional = Optional.ofNullable(decorationRepository.findDecorationByDecorationId(decorationId));
        if (decorationOptional.isPresent()) {
            return decorationOptional.get();
        } else {
            throw new ResourceNotFoundException("Decoration with Id " + decorationId + " not found.");
        }
    }

    @Override
    public Page<Decoration> getDecorationsBy(String category, String name, boolean order, Pageable pageable) {
        if (category != null && !category.equals("null")) {
            String upperCaseCategory = category.toUpperCase();
            if (!DecorationCategory.contains(upperCaseCategory))
                throw new ResourceNotFoundException("Category " + category + " not found.");
        }
        if (category != null && category.equals("null"))
            category = null;
        List<Decoration> decorations = new ArrayList<>();
        if (category != null){
            if(name != null){
                decorations = decorationRepository.findAllByCategoryAndDecorationName(DecorationCategory.valueOf(category.toUpperCase()), name);
            }
            else {
                decorations = decorationRepository.findAllByCategory(DecorationCategory.valueOf(category.toUpperCase()));
            }
        }
        else if (name != null){
            decorations = decorationRepository.findAllByDecorationName(name);
        }
        else {
            decorations = decorationRepository.findAll();
        }
        decorations = decorations.stream()
                .sorted(order? Comparator.comparingDouble(Decoration::getPrice).reversed() : Comparator.comparingDouble(Decoration::getPrice))
                .collect(Collectors.toList());

        return findPaginated(decorations, pageable);
    }

    private Page<Decoration> findPaginated(List<Decoration> products, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Decoration> result;

        if (products.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            result = products.subList(startItem, toIndex);
        }

        return new PageImpl<>(result, PageRequest.of(currentPage, pageSize), products.size());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Decoration> decorationOptional = Optional.ofNullable(decorationRepository.findDecorationByDecorationId(id));
        if (!decorationOptional.isPresent()) {
            throw new RuntimeException("Product not found!");
        }
        Decoration decoration = decorationOptional.get();

        decorationRepository.deleteByDecorationId(id);
    }

}
