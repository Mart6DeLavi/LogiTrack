package com.logitrack.shippingservice.dao;

import com.logitrack.shippingservice.entities.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DeliveryServiceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addDeliveryService(DeliveryService deliveryService) {
        String sql = "INSERT INTO delivery_service (service_name, service_reputation) VALUES (?, ?)";
        jdbcTemplate.update(
                sql,
                deliveryService.getServiceName(),
                deliveryService.getServiceReputation()
        );
    }

    public List<DeliveryService> getAllDeliveryServices() {
        String sql = "SELECT * FROM delivery_service";
        return jdbcTemplate.queryForList(sql).stream()
                .map(row -> {
                    DeliveryService deliveryService = new DeliveryService();
                    deliveryService.setServiceId((Integer) row.get("service_id"));
                    deliveryService.setServiceName((String) row.get("service_name"));
                    deliveryService.setServiceReputation((Double) row.get("service_reputation"));
                    return deliveryService;
                })
                .collect(Collectors.toList());
    }

    public void updateServiceReputation(int serviceId, int reputation) {
        String sql = "UPDATE delivery_service SET service_reputation = ? WHERE service_id = ?";
        jdbcTemplate.update(sql, reputation, serviceId);
    }

    public void deleteServiceById(int serviceId) {
        String sql = "DELETE FROM delivery_service WHERE service_id = ?";
        jdbcTemplate.update(sql, serviceId);
    }
}
