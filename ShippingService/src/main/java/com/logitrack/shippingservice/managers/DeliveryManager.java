package com.logitrack.shippingservice.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logitrack.shippingservice.dao.DeliveryDao;
import com.logitrack.shippingservice.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryManager {

    @Autowired
    private DeliveryDao deliveryDao;

    public void addDelivery(DeliveryEntity deliveryEntity) throws JsonProcessingException {
        deliveryDao.addDelivery(deliveryEntity);
    }

    public List<DeliveryEntity> getAllDeliveryByUserId(long userId) {
        return deliveryDao.getAllDeliveryByUserId(userId);
    }

    public void updateDeliveryAddress(DeliveryEntity deliveryEntity) {
        deliveryDao.updateDeliveryAddress(deliveryEntity);
    }

    public void updateDeliveryServiceId(DeliveryEntity deliveryEntity) {
        deliveryDao.updateDeliveryServiceId(deliveryEntity);
    }

    public void deleteDeliveryById(long deliveryId) {
        deliveryDao.deleteDeliveryById(deliveryId);
    }
}
