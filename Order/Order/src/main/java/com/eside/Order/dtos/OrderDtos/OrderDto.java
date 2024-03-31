package com.eside.Order.dtos.OrderDtos;


import com.eside.Order.enums.OrderStatusEnum;
import com.eside.Order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDto {
    private Long orderId;

    private Date orderDate;

    private OrderStatusEnum orderStatus;

    private Long advertisementId;

    private Long accountId;

    public static List<OrderDto> customListMapping(List<Order> orders) {
        if(orders==null) return null;
        ArrayList<OrderDto> orderDtoArrayList = new ArrayList<>();
        for (Order order : orders){
            OrderDto orderDto = customMapping(order);
            orderDtoArrayList.add(orderDto);
        }
        return orderDtoArrayList;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", advertisementId=" + advertisementId +
                ", accountId=" + accountId +
                '}';
    }
    public static OrderDto customMapping (Order order){
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .accountId(order.getAccountId())
                .advertisementId(order.getAdvertisementId())
                .build();
    }
}
