package veb_labs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String capacity;
    private String description;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    public Location(String name, String address, String capacity, String description) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }
}
