package br.com.selectgearmotors.vehicle.infrastructure.entity.media;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.AuditDomain;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_media", schema = "vehicle")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Tag(name = "Media object")
public class MediaEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    private String path;

    @Column(name = "media_type", length = 255)
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    public void update(Long id, MediaEntity mediaEntity) {
        this.id = id;
        this.name = mediaEntity.getName();
        this.mediaType = mediaEntity.getMediaType();
        this.path = mediaEntity.getPath();
    }
}
