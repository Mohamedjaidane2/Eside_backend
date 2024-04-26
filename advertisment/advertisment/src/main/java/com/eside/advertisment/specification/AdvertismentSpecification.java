package com.eside.advertisment.specification;

import com.eside.advertisment.dtos.FilterDto;
import com.eside.advertisment.model.Advertisment;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class  AdvertismentSpecification{

    public static Specification<Advertisment> createSpecification(List<FilterDto> filterDtoList) {
        return new Specification<Advertisment>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Advertisment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (filterDtoList.isEmpty()) {
                    return criteriaBuilder.conjunction(); // Returns an empty predicate
                }
                List<Predicate> predicates = new ArrayList<>();
                for (FilterDto filterDto : filterDtoList) {
                    String columnName = filterDto.getColumnName();
                    List<Object> columnValues = filterDto.getColumnValue();
                    Path<?> columnPath = getColumnPath(root, columnName);
                    if (columnPath != null) {
                        List<Predicate> valuePredicates = new ArrayList<>();
                        for (Object value : columnValues) {
                            Predicate predicate = criteriaBuilder.equal(columnPath, value);
                            valuePredicates.add(predicate);
                        }
                        predicates.add(criteriaBuilder.or(valuePredicates.toArray(new Predicate[0])));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            private Path<?> getColumnPath(Root<Advertisment> root, String columnName) {
                if (columnName.equals("category")) {
                    return root.join("product").join("subCategory").join("Category").get("name");
                }
                if (columnName.equals("subCategory")) {
                    return root.join("product").join("subCategory").get("name");
                }
                if ("productStatus".equals(columnName)) {
                    return root.join("product").get("productStatus");
                } if ("color".equals(columnName)) {
                    return root.join("product").get("color");
                }
                else {
                    return root.get(columnName);
                }
                // Add more conditions for other columns if needed
            }
        };
    }
}