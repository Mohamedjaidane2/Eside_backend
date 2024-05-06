package com.eside.account.externalDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageDto {
    private Long id;

    private String name;

    private String type;

    private String path;

}
