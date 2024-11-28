package com.logitrack.shippingservice.controllers;

import com.logitrack.shippingservice.entities.DeliveryService;
import com.logitrack.shippingservice.managers.DeliveryServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-service")
public class DeliveryServiceController {

    @Autowired
    private DeliveryServiceManager deliveryServiceManager;

    @PostMapping("/create")
    public ResponseEntity<String> addService(@RequestBody DeliveryService deliveryService) {
        deliveryServiceManager.addService(deliveryService);
        return ResponseEntity.ok("Service created");
    }

    @GetMapping("/all")
    public List<DeliveryService> getAllServices() {
        return deliveryServiceManager.getAllServices();
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<String> updateReputation(
            @PathVariable int serviceId,
            @RequestParam int reputation
    ) {
        deliveryServiceManager.updateReputation(serviceId, reputation);
        return ResponseEntity.ok("Service updated");
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> deleteService(@PathVariable int serviceId) {
        deliveryServiceManager.deleteService(serviceId);
        return ResponseEntity.ok("Service deleted");
    }
}
