package cn.xctra.xaufeholebackend.database.repositories;

import cn.xctra.xaufeholebackend.database.entities.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p where ?1 member of p.attributes")
    List<PostEntity> findAllByAttributesContains(String attribute);

    @Query("SELECT p FROM PostEntity p JOIN p.starredUsers u WHERE u.id = :userId")
    List<PostEntity> findByStarredUsersId(long userId, Pageable pageable);

    @Query("SELECT p FROM PostEntity p WHERE p.poster.id = :posterId")
    List<PostEntity> findByPosterId(long posterId, Pageable pageable);
}
