package com.eside.account.dtos.InformationDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InformationUpdateDto {

    private String profilePicture;

    private String bio;

    private String phoneNumber;

    private String address ;

    private String optionalAddress ;

    private int postalCode ;

    private String city ;

}
