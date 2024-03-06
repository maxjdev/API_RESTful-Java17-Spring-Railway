package br.com.controller.dto;

import br.com.domain.model.News;

public record NewsDTO(Long id, String icon, String description) {

    public NewsDTO(News model) {
        this(model.getId(), model.getIcon(), model.getDescription());
    }

    public News toModel() {
        News model = new News();
        model.setId(this.id);
        model.setIcon(this.icon);
        model.setDescription(this.description);
        return model;
    }
}
