package br.com.selectgearmotors.vehicle.core.domain;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
@Tag(name = "Media object")
public class Media implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1", required = true)
    private Long id;

    private String name;

    private String path;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    public void update(Long id, Media media) {
        this.id = id;
        this.name = media.getName();
        this.mediaType = media.getMediaType();
        this.path = media.getPath();
    }
}
