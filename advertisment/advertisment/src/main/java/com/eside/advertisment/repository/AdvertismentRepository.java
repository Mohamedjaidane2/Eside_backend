package com.eside.advertisment.repository;

import com.eside.advertisment.enums.AdvertisementStatusEnum;
import com.eside.advertisment.model.Advertisment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdvertismentRepository extends JpaRepository<Advertisment, Long>, JpaSpecificationExecutor<Advertisment> {

    List<Advertisment> findByUserAccountId(Long id);
    List<Advertisment> findByUserAccountIdAndAdvertisementStatusEnum(Long id,AdvertisementStatusEnum status);

    List<Advertisment> findAllByUserAccountIdNot(Long userAccountId);
    Page<Advertisment> findAllByUserAccountIdNotAndAdvertisementStatusEnum(Long userAccountId ,Pageable pageable, AdvertisementStatusEnum status );

    Page<Advertisment> findAllByProduct_SubCategory_NameAndUserAccountIdNotAndAdvertisementStatusEnum(String categoryName, Long userAccountId, Pageable pageable,AdvertisementStatusEnum status);
    Page<Advertisment> findAllByProduct_SubCategory_NameAndAdvertisementStatusEnum(String categoryName, Pageable pageable,AdvertisementStatusEnum status);
    List<Advertisment> findAllByProduct_SubCategory_NameAndUserAccountIdNotAndAdvertisementStatusEnum(String categoryName, Long userAccountId,AdvertisementStatusEnum status);
    List<Advertisment> findAllByProduct_SubCategory_NameAndAdvertisementStatusEnum(String categoryName,AdvertisementStatusEnum status);

    // Top 10 newest advertisements excluding the user's own advertisements
    List<Advertisment> findTop10ByProduct_SubCategory_NameAndAdvertisementStatusEnumOrderByCreationDateDesc(String product_subCategory_name, AdvertisementStatusEnum advertisementStatusEnum);
    List<Advertisment> findTop10ByProduct_SubCategory_NameAndUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(String product_subCategory_name, Long userAccountId, AdvertisementStatusEnum advertisementStatusEnum);
    List<Advertisment> findTop10ByUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(Long userAccountId, AdvertisementStatusEnum status);
    List<Advertisment> findTop10ByAdvertisementStatusEnumOrderByCreationDateDesc(AdvertisementStatusEnum status);
   // List<Advertisment> findByUserAccountIdAndAdvertisementStatusEnum(Long userAccountId, AdvertisementStatusEnum status);

    // Find all advertisements ordered by creation date excluding the user's own advertisements
    List<Advertisment> findByUserAccountIdNotAndAdvertisementStatusEnumOrderByCreationDateDesc(Long userAccountId,AdvertisementStatusEnum status);

}
