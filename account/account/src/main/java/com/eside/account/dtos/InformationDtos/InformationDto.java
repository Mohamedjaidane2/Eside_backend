package com.eside.account.dtos.InformationDtos;

import com.eside.account.model.Information;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InformationDto {

    private Long id;

    private String profilePicture;

    private String bio;

    private String phoneNumber;

    private String address ;

    private String optionalAddress ;

    private int postalCode ;

    private String city ;

    private Date creationDate;

    private Date updateDate;

    private String accountName;

    private Long accountId;

    public static InformationDto customMapping(Information information) {
        return InformationDto.builder()
                .id(information.getId())
                .profilePicture(information.getProfilePicture())
                .bio(information.getBio())
                .phoneNumber(information.getPhoneNumber())
                .address(information.getAddress())
                .optionalAddress(information.getOptionalAddress())
                .city(information.getCity())
                .postalCode(information.getPostalCode())
                .creationDate(information.getCreationDate())
                .updateDate(information.getUpdateDate())
                .accountName(information.getAccount().getAccountName())
                .accountId(information.getAccount().getId())
                .build();
    }
}
