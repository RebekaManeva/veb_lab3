package veb_labs.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import veb_labs.model.Location;
import veb_labs.repository.jpa.LocationRepository;
import veb_labs.repository.inMemory.InMemoryLocationRepository;
import veb_labs.service.LocationService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final InMemoryLocationRepository inMemoryLocationRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }


}
