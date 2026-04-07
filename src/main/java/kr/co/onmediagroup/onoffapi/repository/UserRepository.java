package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
  Optional<UserEntity> findBySocialId(String socialId);

  Optional<UserEntity> findBySocialIdAndSocialProvider(String id, User.UserSocialProvider userSocialProvider);
}
