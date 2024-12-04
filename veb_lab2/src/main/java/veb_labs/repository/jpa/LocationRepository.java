package veb_labs.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import veb_labs.model.Location;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
