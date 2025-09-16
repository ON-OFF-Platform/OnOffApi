package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, String> {
}
