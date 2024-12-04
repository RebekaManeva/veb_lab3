package veb_labs.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb_labs.model.Event;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventById(Long id);
    List<Event> findAllByPopularityScore(double popularityScore);
    List<Event> findAllByDescription(String description);
    List<Event> findAllByLocation_Id(Long locationId);
}
