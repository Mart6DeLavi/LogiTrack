package com.logitrack.shippingservice.managers;

import com.logitrack.shippingservice.dao.DeliveryServiceDao;
import com.logitrack.shippingservice.entities.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceManager {

    @Autowired
    private DeliveryServiceDao deliveryServiceDao;

    public void addService(DeliveryService service) {
        deliveryServiceDao.addDeliveryService(service);
    }

    public List<DeliveryService> getAllServices() {
        return deliveryServiceDao.getAllDeliveryServices();
    }

    public void updateReputation(int serviceId, int reputation) {
        deliveryServiceDao.updateServiceReputation(serviceId, reputation);
    }

    public void deleteService(int serviceId) {
        deliveryServiceDao.deleteServiceById(serviceId);
    }
}
