package com.logitrack.shippingservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logitrack.shippingservice.entities.DeliveryEntity;
import com.logitrack.shippingservice.managers.DeliveryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryManager deliveryManager;

    @PostMapping("/create")
    public ResponseEntity<String> addDelivery(@RequestBody final DeliveryEntity deliveryEntity) throws JsonProcessingException {
        deliveryManager.addDelivery(deliveryEntity);
        return ResponseEntity.ok("Delivery created");
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<DeliveryEntity>> getAllDeliveryByUserId(@PathVariable final long userId) {
        return ResponseEntity.ok(deliveryManager.getAllDeliveryByUserId(userId));
    }

    @PatchMapping("/update-address")
    public ResponseEntity<String> updateDeliveryAddress(final DeliveryEntity deliveryEntity) {
        deliveryManager.updateDeliveryAddress(deliveryEntity);
        return ResponseEntity.ok("Delivery address updated");
    }

    @PatchMapping("/update-service-id")
    public ResponseEntity<String> updateServiceId(final DeliveryEntity deliveryEntity) {
        deliveryManager.updateDeliveryAddress(deliveryEntity);
        return ResponseEntity.ok("Delivery service id updated");
    }

    @DeleteMapping("/delete/{deliveryId}")
    public ResponseEntity<String> deleteDelivery(@PathVariable final long deliveryId) {
        deliveryManager.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery deleted");
    }
}
