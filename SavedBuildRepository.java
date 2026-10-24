package com.aipc.planner.repository;
import com.aipc.planner.model.SavedBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SavedBuildRepository extends JpaRepository<SavedBuild, Long> {
    List<SavedBuild> findByUserIdOrderByCreatedAtDesc(Long userId);
}
