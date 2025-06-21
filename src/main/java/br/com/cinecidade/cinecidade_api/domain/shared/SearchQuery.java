package br.com.cinecidade.cinecidade_api.domain.shared;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {}
