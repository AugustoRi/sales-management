package com.slmanagement.salesmanagement.controllers;

import com.slmanagement.salesmanagement.dtos.requests.SaleRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.ClientSaleResponseDTO;
import com.slmanagement.salesmanagement.services.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Sales")
@RestController()
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @ApiOperation(value = "List sales per client", nickname = "listSalePerClient")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientSaleResponseDTO> listSalePerClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(saleService.listSalePerClient(clientId));
    }

    @ApiOperation(value = "List sales per id", nickname = "listSalePerId")
    @GetMapping("/{saleId}")
    public ResponseEntity<ClientSaleResponseDTO> listSalePerId(@PathVariable Long saleId) {
        return ResponseEntity.ok(saleService.listSalePerId(saleId));
    }

    @ApiOperation(value = "Save sale", nickname = "saveSale")
    @PostMapping("/client/{clientId}")
    public ResponseEntity<ClientSaleResponseDTO> save(@PathVariable Long clientId, @Valid @RequestBody SaleRequestDTO saleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(clientId, saleDto));
    }

    @ApiOperation(value = "Update sale", nickname = "updateSale")
    @PutMapping("/{saleId}/client/{clientId}")
    public ResponseEntity<ClientSaleResponseDTO> update(@PathVariable Long saleId, @PathVariable Long clientId, @Valid @RequestBody SaleRequestDTO saleDto) {
        return ResponseEntity.ok(saleService.update(saleId, clientId, saleDto));
    }

    @ApiOperation(value = "Delete sale", nickname = "deleteSale")
    @DeleteMapping("/{saleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long saleId) {
        saleService.delete(saleId);
    }
}
