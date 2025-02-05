package com.typanime.typanime_backend.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatDTO {
    private int accuracy;
    private int speed;
    private String Username;
    
    @Nullable
    private Long synopsisId;

    @Nullable
    private Long citationId;

    @Nullable
    private Long challengeId;

    
}
