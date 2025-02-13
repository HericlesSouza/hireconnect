package com.hireconnect.infra.config;

import com.hireconnect.adapters.dto.user.UserWithFreelancerDTO;
import com.hireconnect.core.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        mapper.createTypeMap(User.class, UserWithFreelancerDTO.class)
                .setConverter(context -> {
                    User source = context.getSource();
                    UserWithFreelancerDTO destination = new UserWithFreelancerDTO();

                    destination.setId(source.getId());
                    destination.setName(source.getName());
                    destination.setEmail(source.getEmail());
                    destination.setImgUrl(source.getImgUrl());
                    destination.setTypeUser(source.getTypeUser());
                    destination.setCreatedAt(source.getCreatedAt());
                    destination.setUpdatedAt(source.getUpdatedAt());

                    if (source.getFreelancer() != null) {
                        destination.setSpecialization(source.getFreelancer().getSpecialization());
                        destination.setBio(source.getFreelancer().getBio());
                    }

                    return destination;
                });

        return mapper;
    }
}
