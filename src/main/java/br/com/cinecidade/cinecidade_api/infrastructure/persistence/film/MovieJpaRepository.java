package br.com.cinecidade.cinecidade_api.infrastructure.persistence.film;

import br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {}
