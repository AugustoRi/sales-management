package com.slmanagement.salesmanagement.services;

import com.slmanagement.salesmanagement.dtos.requests.SaleItemRequestDTO;
import com.slmanagement.salesmanagement.dtos.requests.SaleRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.ClientSaleResponseDTO;
import com.slmanagement.salesmanagement.dtos.responses.SaleResponseDTO;
import com.slmanagement.salesmanagement.entities.Client;
import com.slmanagement.salesmanagement.entities.Product;
import com.slmanagement.salesmanagement.entities.Sale;
import com.slmanagement.salesmanagement.entities.SaleItem;
import com.slmanagement.salesmanagement.exceptions.BusinessRuleException;
import com.slmanagement.salesmanagement.repositories.SaleItemRepository;
import com.slmanagement.salesmanagement.repositories.SaleRepository;
import com.slmanagement.salesmanagement.services.abstracts.AbstractSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService extends AbstractSaleService {
    private SaleRepository saleRepository;
    private SaleItemRepository saleItemRepository;
    private ClientService clientService;
    private ProductService productService;

    @Autowired
    public SaleService(SaleRepository saleRepository, SaleItemRepository saleItemRepository, ClientService clientService, ProductService productService) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.clientService = clientService;
        this.productService = productService;
    }

    public ClientSaleResponseDTO listSalePerClient(Long clientId) {
        Client client = handleCheckClient(clientId);
        List<SaleResponseDTO> handleGetSaleResponseDtoList = saleRepository
                .findByClientId(clientId)
                .stream()
                .map(sale -> handleCreateSaleResponseDTO(sale, saleItemRepository.findBySalePerId(sale.getId())))
                .collect(Collectors.toList());

        return new ClientSaleResponseDTO(
                client.getName(),
                handleGetSaleResponseDtoList
        );
    }

    public ClientSaleResponseDTO listSalePerId(Long saleId) {
        Sale sale = handleCheckSale(saleId);
        List<SaleItem> saleItemList = saleItemRepository.findBySalePerId(sale.getId());
        return handleReturnClientSaleResponseDTO(sale, saleItemList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ClientSaleResponseDTO save(Long clientId, SaleRequestDTO saleDto) {
        Client client = handleCheckClient(clientId);
        handleCheckProductAndUpdateQuantity(saleDto.getSaleItemDTO());
        Sale saleSaved = saveSale(client, saleDto);
        return handleReturnClientSaleResponseDTO(saleSaved, saleItemRepository.findBySalePerId(saleSaved.getId()));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ClientSaleResponseDTO update(Long saleId, Long clientId, SaleRequestDTO saleDto) {
        handleCheckSale(saleId);
        Client client = handleCheckClient(clientId);
        List<SaleItem> saleItemList = saleItemRepository.findBySalePerId(saleId);
        handleCheckProductAndReturnToStock(saleItemList);
        handleCheckProductAndUpdateQuantity(saleDto.getSaleItemDTO());
        Sale saleUpdated = updateSale(saleId, client, saleDto);
        return handleReturnClientSaleResponseDTO(saleUpdated, saleItemRepository.findBySalePerId(saleUpdated.getId()));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long saleId) {
        handleCheckSale(saleId);
        List<SaleItem> saleItems = saleItemRepository.findBySalePerId(saleId);
        handleCheckProductAndReturnToStock(saleItems);
        saleItemRepository.deleteAll(saleItems);
        saleRepository.deleteById(saleId);
    }

    private void handleCheckProductAndReturnToStock(List<SaleItem> saleItems) {
        saleItems.forEach(item -> {
            Product product = productService.handleCheckProduct(item.getProduct().getId());
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productService.handleUpdateQuantityInStock(product);
        });
    }

    private Sale saveSale(Client client, SaleRequestDTO saleDto) {
        Sale saleSaved = saleRepository.save(
                new Sale(saleDto.getDate(), client)
        );

        saleDto
                .getSaleItemDTO()
                .stream()
                .map(saleItemDto -> handleCreateSaleItem(saleItemDto, saleSaved))
                .forEach(saleItemRepository::save);

        return saleSaved;
    }

    private Sale updateSale(Long saleId, Client client, SaleRequestDTO saleDto) {
        Sale saleSaved = saleRepository.save(
                new Sale(saleId, saleDto.getDate(), client)
        );

        saleDto
                .getSaleItemDTO()
                .stream()
                .map(saleItemDto -> handleCreateSaleItem(saleItemDto, saleSaved))
                .forEach(saleItemRepository::save);

        return saleSaved;
    }

    private void handleCheckProductAndUpdateQuantity(List<SaleItemRequestDTO> saleItemsDto) {
        saleItemsDto.forEach(item -> {
                    Product product = productService.handleCheckProduct(item.getProductId());
                    handleCheckProductQuantity(product, item.getQuantity());
                    product.setQuantity(product.getQuantity() - item.getQuantity());
                    productService.handleUpdateQuantityInStock(product);
        });
    }

    private void handleCheckProductQuantity(Product product, Integer quantitySaleDto) {
        if(product.getQuantity() < quantitySaleDto) {
            throw new BusinessRuleException(
                    String.format("A quantidade %s informada para o produto %s não está disponível em estoque",
                            quantitySaleDto, product.getDescription()
                    )
            );
        }
    }

    private Sale handleCheckSale(Long saleId) {
        Optional<Sale> handleGetSale = saleRepository.findById(saleId);

        if (handleGetSale.isEmpty()) {
            throw new BusinessRuleException(String.format("Venda de código %s não encontrada.", saleId));
        }

        return handleGetSale.get();
    }

    private Client handleCheckClient(Long id) {
        Optional<Client> handleGetClient = clientService.findById(id);

        if (handleGetClient.isEmpty()) {
            throw new BusinessRuleException(String.format("O cliente de código %s informado não existe no cadastro.", id));
        }

        return handleGetClient.get();
    }
}
