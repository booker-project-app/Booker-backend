package booker.BookingApp.model.accommodation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
@Entity @JsonIgnoreProperties(value= {"accommodation"})
public @Data @AllArgsConstructor @NoArgsConstructor
class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @JoinColumn(name = "image_path")
    private String image_path;

    @Override
    public String toString() {
        return "Amenity: id=" + id + ", name=" + name;
    }
}
