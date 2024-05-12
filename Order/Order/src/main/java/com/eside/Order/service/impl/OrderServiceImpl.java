package com.eside.Order.service.impl;

import com.eside.Order.client.AccountClient;
import com.eside.Order.client.AdvertismentClient;
import com.eside.Order.client.WalletClient;
import com.eside.Order.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.Order.dtos.OrderDtos.OrderDto;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.enums.AdvertisementSoldStatusEnum;
import com.eside.Order.enums.DiscountRequestStatus;
import com.eside.Order.enums.OrderStatusEnum;
import com.eside.Order.exception.EntityNotFoundException;
import com.eside.Order.exception.InvalidOperationException;
import com.eside.Order.externalData.Account;
import com.eside.Order.externalData.Advertisment;
import com.eside.Order.externalData.WalletActionDto;
import com.eside.Order.externalData.WalletDto;
import com.eside.Order.model.Order;
import com.eside.Order.repository.OrderRepository;
import com.eside.Order.service.OrderService;
import com.eside.Order.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository ;
    private final AccountClient accountClient;
    private final AdvertismentClient advertismentClient;
    private final WalletClient walletClient;
    private final DiscountRequestServiceImpl discountRequestService;


    @Override
    public OrderDto toOrder(Long accountId, Long advertisementId) {
        Account account = new Account();
        Advertisment advertisment = new Advertisment();
        try {
            account= accountClient.getAccountByIdFromOrder(accountId);
            //System.out.println("our account " + account);
        }catch (Exception e){
            throw e ;
        }
        try {
            advertisment= advertismentClient.getAdvertismentByIdFromOrder(advertisementId);
            //System.out.println("our advertisment " + advertisment);
        }catch (Exception e){
            throw e ;
        }
        try {

        }catch (Exception e){
            throw e ;
        }

        //Check if the account has already placed an order for the specified advertisement
        //boolean hasPlacedOrder = orderRepository.existsBySenderIdAndAdvertisementId(accountId, advertisementId);
        List<Order> orderList = orderRepository.findAllBySenderIdAndAdvertisementIdOrderByOrderDateDesc(accountId, advertisementId);
        for(Order order : orderList){
            if (order.getOrderStatus()!= OrderStatusEnum.CANCELLED && order.getDiscountRequest()!=null && order.getDiscountRequest().getEDiscountRequestStats()!=DiscountRequestStatus.DECLINED){
                throw new InvalidOperationException("You have already placed an order for this advertisement");
            }
        }

        // Check if the advertisement belongs to the same account
        if (advertisment.getUserAccountId().equals(accountId)) {
            throw new InvalidOperationException("You can't place an order on your own advertisement");
        }

        boolean is_available = advertismentClient.isAvailable(advertisementId);
        //System.out.println("our advertisment " + advertisment);
        if (!is_available){
            throw new InvalidOperationException("this advertisment is already ordred !");
        }

        //if (advertisment.getOrderId() != null) {
        //    throw new InvalidOperationException("Someone else has already ordered this advertisement");
        //}
        // Create a new Order using the builder pattern
        Order order = Order.builder()
                .senderId(accountId)
                .reciverId(advertisment.getUserAccountId())
                .advertisementId(advertisementId)
                .orderStatus(OrderStatusEnum.AWAITING_CONFIRMATION)
                .orderAmount(advertisment.getPrice())
                .seller_benefits(sellerBenefits(advertisment.getPrice()))
                .build();

        // Save the created Order
        orderRepository.save(order);

        return OrderDto.customMapping(order);
    }

    double sellerBenefits(double orderAmount) {
        DecimalFormat df = new DecimalFormat("#.###"); // Define the format for three decimal places
        if (orderAmount >= 300) {
            // Calculate 15% of the order amount
            double benefit = orderAmount-orderAmount * 0.15;
            return Double.parseDouble(df.format(benefit)); // Limit the result to three decimal places
        } else if (orderAmount < 200) {
            // Calculate 20% of the order amount
            double benefit = orderAmount-orderAmount * 0.20;
            return Double.parseDouble(df.format(benefit)); // Limit the result to three decimal places
        } else {
            // If the order amount is between 200 and 300, no benefit is applied
            return 0;
        }
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        return OrderDto.customMapping(order);
    }

    @Override
    public List<OrderDto> getOrderByAccount(Long accountId) {
        List<Order> output = new ArrayList<>();
        List<Order> orders = orderRepository.findAllBySenderIdOrderByOrderDateDesc(accountId);
        for(Order o : orders){
            if (o.getDiscountRequest()==null || o.getDiscountRequest().getEDiscountRequestStats() == DiscountRequestStatus.ACCEPTED){
                output.add(o);
            }
        }
        return OrderDto.customListMapping(output);
    }

    @Override
    public SuccessDto cancelOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        order.setOrderStatus(OrderStatusEnum.CANCELLED);
        List<Order> orderList = orderRepository.findAllByAdvertisementId(order.getAdvertisementId());
        for (Order o : orderList){
            if(!Objects.equals(o.getOrderId(), orderId)){
                if(o.getOrderStatus()==OrderStatusEnum.LISTED_FOR_WAITING){
                    o.setOrderStatus(OrderStatusEnum.AWAITING_CONFIRMATION);
                }
                orderRepository.save(o);
            }
        }
        try {
            advertismentClient.deleteOrder(order.getAdvertisementId());

        } catch (Exception e) {
            // Gérer l'exception de manière appropriée (par exemple, journalisation ou remontée)
            throw new RuntimeException("Failed to update advertisement status", e);
        }
        orderRepository.save(order);
        return SuccessDto.builder()
                .message("Order placed Cancelled !")
                .build();
    }
    @Override
    public SuccessDto confirmOrder(Long orderId) {
        // Récupérer la commande
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        advertismentClient.deleteOrder(order.getAdvertisementId());
        // Mettre à jour l'état de l'annonce associée à la commande
        updateAdvertisementStatus(order);

        // Mettre à jour l'état de la commande actuelle
        order.setOrderStatus(OrderStatusEnum.CONFIRMED);
        orderRepository.save(order);

        // Mettre à jour l'état des autres commandes en attente
        updateWaitingOrders(order);

        return SuccessDto.builder()
                .message("Order Confirmed successfully !")
                .build();
    }

    private void updateAdvertisementStatus(Order order) {
        try {
            Advertisment advertisment = advertismentClient.getAdvertismentByIdFromOrder(order.getAdvertisementId());
            advertisment.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.IN_PROGRESS);
            advertismentClient.UpdateAdvertismentStatusFromOrder(order.getOrderId(), order.getAdvertisementId());
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée (par exemple, journalisation ou remontée)
            throw new RuntimeException("Failed to update advertisement status", e);
        }
    }

    private void updateWaitingOrders(Order confirmedOrder) {
        List<Order> orderList = orderRepository.findAllByAdvertisementId(confirmedOrder.getAdvertisementId());
        for (Order order : orderList) {
            if (!Objects.equals(order.getOrderId(), confirmedOrder.getOrderId())) {
                if(order.getOrderStatus()==OrderStatusEnum.AWAITING_CONFIRMATION){
                    order.setOrderStatus(OrderStatusEnum.LISTED_FOR_WAITING);
                }
                orderRepository.save(order);
            }
        }
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getRecivedOrder(Long accountId) {
        List<OrderDto>filtredList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAllByReciverIdOrderByOrderDateDesc(accountId);
        for(Order o : orderList){
           // if(o.getOrderStatus()!=OrderStatusEnum.AWAITING_CONFIRMATION){
                filtredList.add(OrderDto.customMapping(o));
            //}
        }
        return filtredList;
    }




    @Override
    public String getOrderProgressStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        return order.getOrderStatus().name();
    }

    @Override
    public SuccessDto deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> {
                            throw  new EntityNotFoundException("order not found");
                        }
                );
        if (order.getOrderStatus()!=OrderStatusEnum.AWAITING_CONFIRMATION && order.getOrderStatus()!=OrderStatusEnum.CONFIRMED){
            throw new InvalidOperationException("you cannot delete an active Order");
        }
        if(order.getDiscountRequest()!=null){
        discountRequestService.deleteDiscountRequestById(order.getDiscountRequest().getId());
        }
        orderRepository.delete(order);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }

    @Override
    public SuccessDto changeStatusByDeliveryProvider(Long orderId, OrderStatusEnum orderStatusEnum) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("order not found")
                );
        order.setOrderStatus(orderStatusEnum);
        SuccessDto successDto = SuccessDto.builder().message("Status Changed").build();
        if(orderStatusEnum==OrderStatusEnum.PAYMENT_RECEIVED){
            WalletDto walletDto = walletClient.getWalletByAccountId(order.getReciverId());
            SuccessDto addfunds = walletClient.addFundsToWalletFromOrder(WalletActionDto.builder().walletId(walletDto.getId()).amount(order.getSeller_benefits()).build());
        successDto.setMessage(addfunds.getMessage());

                Advertisment advertisment = advertismentClient.getAdvertismentByIdFromOrder(order.getAdvertisementId());
                advertisment.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.SOLD);
                advertismentClient.changerAdvertismentStatusWhilePayment(order.getOrderId(), order.getAdvertisementId());

        }
        orderRepository.save(order);
        return successDto;
    }
}
