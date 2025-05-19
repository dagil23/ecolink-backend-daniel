package com.ecolink.spring.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Startup extends UserBase {

    @Enumerated(EnumType.STRING)
    private Status status;

  
    @OneToMany(mappedBy = "startup")
    List<Proposal> proposals;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Product> products;

    @ManyToMany
    @JoinTable(name = "startup_ods", joinColumns = @JoinColumn(name = "id_startup"), inverseJoinColumns = @JoinColumn(name = "id_ods"))
    private List<Ods> odsList;

    @Column
    private String location;
    private String description;
    
    private Double compability;


    public Startup(String name, List<Ods> odsList, String email, String description, String image, String location) {
        this.name = name;
        this.level = 0L;
        this.odsList = odsList;
        this.userType = UserType.STARTUP;
        this.email = email;
        this.description = description;
        this.status = Status.PENDING;
        this.imageUrl = image;
        this.location = location;
    }

    public void addProposal(Proposal proposal) {
        this.proposals.add(proposal);

        if (proposal.getStartup() == null) {
            proposal.setStartup(this);
        }
    }

    public void addProduct(Product product) {
        product.setStartup(this);
        this.products.add(product);
    }

    public void addOds(Ods ods) {
        this.odsList.add(ods);
    }

    public Double getCompability(){
        return this.compability;
    }
    public void setCompapility(Double compability){
        this.compability = compability;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_STARTUP");
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}