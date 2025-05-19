package com.ecolink.spring.controller;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.PaginationResponse;
import com.ecolink.spring.dto.ProductDTO;
import com.ecolink.spring.dto.ProductSalesDTO;
import com.ecolink.spring.dto.StartupHomeDTO;
import com.ecolink.spring.dto.StartupPublicProfileDTO;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.entity.OrderLine;
import com.ecolink.spring.entity.Product;
import com.ecolink.spring.entity.Proposal;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.exception.CompanyNotFoundException;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.exception.OdsNotFoundException;
import com.ecolink.spring.exception.StartupNotFoundException;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.service.OdsService;
import com.ecolink.spring.service.OrderLineService;
import com.ecolink.spring.service.ProductService;
import com.ecolink.spring.service.StartupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/startup")
public class StartupController {
    private final DTOConverter dtoConverter;
    private final StartupService service;
    private final OdsService odsService;
    private final ProductService productService;
    private final OrderLineService orderLineService;

    @GetMapping()
    public ResponseEntity<?> getStartups(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) List<Long> odsIdList) {

        try {
            List<Ods> odsList = new ArrayList<>();

            if (odsIdList != null && !odsIdList.isEmpty()) {
                odsIdList.forEach(odsId -> {
                    Ods ods = odsService.findById(odsId);
                    if (ods != null) {
                        odsList.add(ods);
                    }
                });
            }

            Page<Startup> startups = service.findByFilterAndPagination(name, odsList, page, size);

            if (startups.isEmpty()) {
                throw new StartupNotFoundException("No se encontraron startups en la página especificada");
            }
            ;

            List<StartupHomeDTO> dtoList = startups.getContent().stream().map(dtoConverter::convertStartupHomeToDto)
                    .collect(Collectors.toList());

            var response = new PaginationResponse<>(
                    dtoList,
                    startups.getNumber(),
                    startups.getSize(),
                    startups.getTotalElements(),
                    startups.getTotalPages(),
                    startups.isLast());

            return ResponseEntity.ok(response);
        } catch (StartupNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStartup(@PathVariable Long id) {
        try {
            Startup startup = service.findById(id);
            if (startup == null) {
                throw new StartupNotFoundException("No existe la startup con id=" + id);
            }
            List<Proposal> startupProposal = startup.getProposals();
            if (startupProposal != null) {
                startupProposal.forEach(proposal -> {

                    System.out.println("Propuestas de startup: " + proposal.getDescription());
                });
            } else {
                System.out.println("No tiene ");
            }
            StartupPublicProfileDTO dto = dtoConverter.convertStartupProfileToDto(startup);

            return ResponseEntity.ok(dto);
        } catch (StartupNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @GetMapping("/home")
    public ResponseEntity<?> getRelevantStartups() {

        try {
            List<Startup> startups = service.findTop3ByOrderByLevelDesc();
            if (startups.isEmpty()) {
                throw new StartupNotFoundException("No se encontraron startups relevantes");
            }

            List<StartupHomeDTO> dtoList = startups.stream().map(dtoConverter::convertStartupHomeToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtoList);
        } catch (StartupNotFoundException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @GetMapping("/product")
    public ResponseEntity<?> getMethodName(@AuthenticationPrincipal UserBase user) {

        try {
            if (!(user instanceof Startup startup)) {
                throw new AccessDeniedException("User is not a startup, only the startup can access this resource");
            }

            List<Product> productsStartup = productService.getStartupProducts(startup);

            if (productsStartup == null || productsStartup.isEmpty()) {
                throw new StartupNotFoundException("The startup does not have any products");

            }
            List<ProductDTO> productStartupDTO = productsStartup.stream().map(dtoConverter::convertProductToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productStartupDTO);
        } catch (StartupNotFoundException e) {

            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);

        } catch (AccessDeniedException e) {

            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);

        } catch (Exception e) {

            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }

    }

    @GetMapping("/sales")
    public ResponseEntity<?> getSalesFromStartup(@AuthenticationPrincipal UserBase user){
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), "The user is not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }
        if (user instanceof Startup startup) {
            List<Product> products = startup.getProducts();
            List<OrderLine> orderLines = new ArrayList<>();
            for (Product product : products) {
                if (product != null) {
                    List<OrderLine> orderLinesProduct = orderLineService.findByProduct(product);
                    if (orderLinesProduct != null) {
                        orderLines.addAll(orderLinesProduct);
                    }
                }
            }
            if (orderLines.size() > 0) {
                List<ProductSalesDTO> productSalesDTO = new ArrayList<>();
                // Obtener la cantidad de ventas de cada producto
                for (Product product : products) {
                    if (product != null) {
                        Long amount = orderLines.stream().filter(orderLine -> orderLine.getProduct().getId() == product.getId())
                                .mapToLong(OrderLine::getAmount).sum();
                        ProductSalesDTO productSales = new ProductSalesDTO();
                        productSales.setName(product.getName());
                        productSales.setAmount(amount);
                        productSales.setTotal(product.getPrice().multiply(new BigDecimal(amount)));
                        productSalesDTO.add(productSales);
                    }
                }
                return ResponseEntity.ok(productSalesDTO);
            } else {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "The startup does not have any sales");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
            }
            
        }

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN.value(), "The user is not a startup");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    //The method to update the ODS of a startup
     @PutMapping("/ods/update")
    public ResponseEntity<?> updateOds(@AuthenticationPrincipal UserBase user, @RequestParam List<Long> odsList) {
        
        if (!(user instanceof Startup)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a startup");
        }

        try {
           

            List<Ods> newOdsUserPreferencesList = new ArrayList<>();
            odsList.forEach(odsId -> {
                Ods ods = odsService.findById(odsId);
                if (ods != null) {
                    newOdsUserPreferencesList.add(ods);
                }
            });

            if (newOdsUserPreferencesList.isEmpty() || newOdsUserPreferencesList.size() == 0) {
                throw new OdsNotFoundException("There is no ODS found with the given ids");
            }

            Startup startuptoUpdate = service.findById(user.getId());
            if (startuptoUpdate == null) {
                throw new CompanyNotFoundException("There is no startup with the given id");      
            }
            startuptoUpdate.setOdsList(newOdsUserPreferencesList);
            service.save(startuptoUpdate);
            SuccessDetails successDetails = new SuccessDetails(HttpStatus.OK.value(), "ODS updated successfully");
            return ResponseEntity.ok(successDetails);

        } catch(CompanyNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }catch(OdsNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
                    e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }

    }
}