package com.inventory.service;

import com.inventory.dto.InventoryDTO;
import com.inventory.entity.Inventory;
import com.inventory.entity.Product;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.exception.InsufficientStockException;
import com.inventory.repository.InventoryRepository;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public InventoryDTO updateStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new ResourceNotFoundException("Inventory not found for product");
        }

        inventory.setQuantity(inventory.getQuantity() + quantity);
        if (inventory.getQuantity() < 0) {
            throw new InsufficientStockException("Insufficient stock available");
        }

        inventory = inventoryRepository.save(inventory);
        InventoryDTO dto = new InventoryDTO();
        BeanUtils.copyProperties(inventory, dto);
        dto.setProductId(productId);
        return dto;
    }

    public InventoryDTO getStock(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new ResourceNotFoundException("Inventory not found for product");
        }

        InventoryDTO dto = new InventoryDTO();
        BeanUtils.copyProperties(inventory, dto);
        dto.setProductId(productId);
        return dto;
    }
}