package com.example.web_mart_be.service.order;

import com.example.web_mart_be.dao.*;
import com.example.web_mart_be.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    private final ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository bookRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    public OrderServiceImp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public ResponseEntity<?> save(JsonNode jsonData) {
        try{

            Order orderData = objectMapper.treeToValue(jsonData, Order.class);
            orderData.setTotalPrice(orderData.getTotalPriceProduct());
            orderData.setDateCreated(Date.valueOf(LocalDate.now()));
            orderData.setStatus("Đang xử lý");

            int idUser = Integer.parseInt(formatStringByJson(String.valueOf(jsonData.get("idUser"))));
            Optional<User> user = userRepository.findById(idUser);
            orderData.setUser(user.get());

            int idPayment = Integer.parseInt(formatStringByJson(String.valueOf(jsonData.get("idPayment"))));
            Optional<Payment> payment = paymentRepository.findById(idPayment);
            orderData.setPayment(payment.get());

            Order newOrder = orderRepository.save(orderData);

            JsonNode jsonNode = jsonData.get("book");
            for (JsonNode node : jsonNode) {
                int quantity = Integer.parseInt(formatStringByJson(String.valueOf(node.get("quantity"))));
                Product bookResponse = objectMapper.treeToValue(node.get("product"), Product.class);
                Optional<Product> product = bookRepository.findById(bookResponse.getIdProduct());
                product.get().setQuantity(product.get().getQuantity() - quantity);
                product.get().setSoldQuantity(product.get().getSoldQuantity() + quantity);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product.get());
                orderDetail.setQuantity(quantity);
                orderDetail.setOrder(newOrder);
                orderDetail.setPrice(quantity * product.get().getSellPrice());
                orderDetailRepository.save(orderDetail);
                bookRepository.save(product.get());
            }

            cartItemRepository.deleteCartItemsByIdUser(user.get().getIdUser());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> update(JsonNode jsonData) {
        try{
            int idOrder =  Integer.parseInt(formatStringByJson(String.valueOf(jsonData.get("idOrder"))));
            String status = formatStringByJson(String.valueOf(jsonData.get("status")));
            Optional<Order> order = orderRepository.findById(idOrder);
            order.get().setStatus(status);

            // Lấy ra order detail
            if (status.equals("Bị huỷ")) {
                List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailsByOrder(order.get());
                for (OrderDetail orderDetail : orderDetailList) {
                   Product productOrderDetail = orderDetail.getProduct();
                    productOrderDetail.setSoldQuantity(productOrderDetail.getSoldQuantity() - orderDetail.getQuantity());
                    productOrderDetail.setQuantity(productOrderDetail.getQuantity() + orderDetail.getQuantity());
                    bookRepository.save(productOrderDetail);
                }
            }

            orderRepository.save(order.get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> cancel(JsonNode jsonData) {
        try{
            int idUser = Integer.parseInt(formatStringByJson(String.valueOf(jsonData.get("idUser"))));
            User user = userRepository.findById(idUser).get();

            Order order = orderRepository.findFirstByUserOrderByIdOrderDesc(user);
            order.setStatus("Bị huỷ");

            List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailsByOrder(order);
            for (OrderDetail orderDetail : orderDetailList) {
                Product productOrderDetail = orderDetail.getProduct();
                productOrderDetail.setSoldQuantity(productOrderDetail.getSoldQuantity() - orderDetail.getQuantity());
                productOrderDetail.setQuantity(productOrderDetail.getQuantity() + orderDetail.getQuantity());
                bookRepository.save(productOrderDetail);
            }

            orderRepository.save(order);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
    private String formatStringByJson(String json) {
        return json.replaceAll("\"", "");
    }
}
