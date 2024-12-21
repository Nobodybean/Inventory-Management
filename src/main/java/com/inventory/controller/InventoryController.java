        package com.inventory.controller;

        import com.inventory.dto.InventoryDTO;
        import com.inventory.service.InventoryService;
        import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.tags.Tag;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "APIs for managing inventory levels")
public class InventoryController {
    private final InventoryService inventoryService;

    @PutMapping("/products/{productId}/stock")
    @Operation(summary = "Update stock level for a product")
    public ResponseEntity<InventoryDTO> updateStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.updateStock(productId, quantity));
    }

    @GetMapping("/products/{productId}/stock")
    @Operation(summary = "Get current stock level for a product")
    public ResponseEntity<InventoryDTO> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }
}

