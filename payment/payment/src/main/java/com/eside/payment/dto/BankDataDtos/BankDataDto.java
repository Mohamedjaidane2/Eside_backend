package com.eside.payment.dto.BankDataDtos;
import com.eside.payment.model.BankData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankDataDto {

    private Long id;

    private String bankName;

    private String rib;

    public static BankDataDto customMapping (BankData bankData){
        return BankDataDto.builder()
                .id(bankData.getId())
                .bankName(bankData.getBankName())
                .rib(bankData.getRib())
                .build();
    }
    public static List<BankDataDto> customListMapping(List<BankData> bankData){
        if (bankData == null) return null;
        ArrayList<BankDataDto> bankDataDtoArrayList = new ArrayList<>();
        for (BankData bankData1 : bankData) {
            BankDataDto bankDataDto = customMapping(bankData1);
            bankDataDtoArrayList.add(bankDataDto);
        }
        return bankDataDtoArrayList;
    }
}
