package com.store.repository;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecorationRepository extends JpaRepository<Decoration, Integer> {
    Decoration findDecorationByDecorationId(Long id);
    List<Decoration> findAll();
    List<Decoration> findAllByCategory(DecorationCategory category);
    List<Decoration> findAllByCategoryAndDecorationName(DecorationCategory category, String name);
    List<Decoration> findAllByDecorationName(String name);
    void deleteByDecorationId(Long id);

}
