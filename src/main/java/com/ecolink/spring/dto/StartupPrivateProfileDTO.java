package com.ecolink.spring.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StartupPrivateProfileDTO {
    String status;
    String name;
    String email;
    String description;
    String location;
    List<OdsDTO> userOdsList;
    List<ProposalStartupProfileDTO> proposals;
    List<StartupProductPrivateProfileDTO> products;
    List<PostProfileUserDTO> listLikePost;
    List<OrderDTO> orders;
    Long level;
    Long xp;
    Long nextLevelXp;
}
