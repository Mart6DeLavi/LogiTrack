package com.logitrack.shippingservice.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.shippingservice.dtos.OrderProductInformationDto;
import com.logitrack.shippingservice.entities.DeliveryEntity;
import com.logitrack.shippingservice.exceptions.MappedProductListIntoJsonException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DeliveryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void addDelivery(DeliveryEntity deliveryEntity) throws JsonProcessingException{
        String sql = "INSERT INTO delivery (delivery_number, delivery_service_id, delivery_address," +
                "order_sum, order_products_list, mongodb_document_id, user_id) VALUES (?, ?, ?, ?, ?::jsonb, ?, ?)";


        String orderProductListJson = objectMapper.writeValueAsString(deliveryEntity.getOrderProducts());
        System.out.println("Serialized json: " + orderProductListJson);
        jdbcTemplate.update(
                sql,
                deliveryEntity.getDeliveryNumber(),
                deliveryEntity.getDeliveryServiceId(),
                deliveryEntity.getDeliveryAddress(),
                deliveryEntity.getOrderSum(),
                orderProductListJson,
                deliveryEntity.getOrderDocumentMongoId(),
                deliveryEntity.getUserId()
        );
    }

    public List<DeliveryEntity> getAllDeliveryByUserId(long userId) {
        String sql = "SELECT * FROM delivery WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            DeliveryEntity deliveryEntity = new DeliveryEntity();
            deliveryEntity.setDeliveryId(rs.getLong("delivery_id"));
            deliveryEntity.setDeliveryNumber(rs.getString("delivery_number"));
            deliveryEntity.setDeliveryServiceId(rs.getInt("delivery_service_id"));
            deliveryEntity.setDeliveryAddress(rs.getString("delivery_address"));
            deliveryEntity.setOrderSum(rs.getDouble("order_sum"));
            deliveryEntity.setOrderProducts(parseOrderProduct(rs.getString("order_products_list")));
            return deliveryEntity;
        });
    }

    @SneakyThrows
    private List<OrderProductInformationDto> parseOrderProduct(String orderProductsJson) {
        if (orderProductsJson == null || orderProductsJson.isBlank()) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(orderProductsJson, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            throw new MappedProductListIntoJsonException(ex);
        }
    }

    public void updateDeliveryAddress(DeliveryEntity deliveryEntity) {
        String sql = "UPDATE delivery SET delivery_address = ? WHERE delivery_id = ?";
        jdbcTemplate.update(
                sql,
                deliveryEntity.getDeliveryId(),
                deliveryEntity.getDeliveryAddress()
        );
    }

    public void updateDeliveryServiceId(DeliveryEntity deliveryEntity) {
        String sql = "UPDATE delivery SET delivery_service_id = ? WHERE delivery_id = ?";
        jdbcTemplate.update(
                sql,
                deliveryEntity.getDeliveryServiceId(),
                deliveryEntity.getDeliveryId());
    }

    public void deleteDeliveryById(long deliveryId) {
        String sql = "DELETE FROM delivery WHERE delivery_id = ?";
        jdbcTemplate.update(sql, deliveryId);
    }
}
