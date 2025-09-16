package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
}
