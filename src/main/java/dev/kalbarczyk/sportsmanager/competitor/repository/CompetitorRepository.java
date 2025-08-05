package dev.kalbarczyk.sportsmanager.competitor.repository;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    Competitor findByName(final String name);
}
