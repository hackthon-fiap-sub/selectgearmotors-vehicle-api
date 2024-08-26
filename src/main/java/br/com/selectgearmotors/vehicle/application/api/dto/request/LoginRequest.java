package br.com.selectgearmotors.vehicle.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LoginRequest", requiredProperties = {"username", "password"})
@Tag(name = "LoginRequest", description = "Model")
public class LoginRequest {
    private String username;
    private String password;
}
