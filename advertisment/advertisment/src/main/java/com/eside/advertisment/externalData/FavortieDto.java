package com.eside.advertisment.externalData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FavortieDto {

    private Long id;
    private Date creationDate;
    private Long advertismentId ;
    private Long accountId ;

}