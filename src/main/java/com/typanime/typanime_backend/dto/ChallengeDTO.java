package com.typanime.typanime_backend.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDTO {

    private String name;

    @Nullable
    private String lifeTime;
    
    @Nullable
    private Long synopsisId;

    @Nullable
    private Long citationId;

}
