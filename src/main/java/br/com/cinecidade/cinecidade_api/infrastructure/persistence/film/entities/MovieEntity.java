package br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "movies")
@Getter
@Setter
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private String rating;
    private String genre;
    private int durationMinutes;
    private String trailerUrl;
    private String status;
    private LocalDateTime createdAt;
}
